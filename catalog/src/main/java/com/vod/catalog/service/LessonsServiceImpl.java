package com.natyaguru.catalog.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natyaguru.catalog.config.AwsConfig;
import com.natyaguru.catalog.dto.Hierarchy;
import com.natyaguru.catalog.dto.LessonDTO;
import com.natyaguru.catalog.entity.Chapters;
import com.natyaguru.catalog.entity.Lessons;
import com.natyaguru.catalog.entity.Modules;
import com.natyaguru.catalog.repository.ChaptersRepository;
import com.natyaguru.catalog.repository.LessonsRepository;
import com.natyaguru.catalog.repository.ModulesRepository;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LessonsServiceImpl implements ILessonsService {

    private static final Logger log = LoggerFactory.getLogger(LessonsServiceImpl.class);
    private static final int maxRetries = 3;

    // The subject line for the email.
    static final String SUBJECT = "Output file not found";

    // The HTML body for the email.
    static final String HTMLBODY = "<h1>FILE NOT FOUND</h1>"
            + "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
            + "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>" + "AWS SDK for Java</a>";

    // The email body for recipients with non-HTML email clients.
    static final String TEXTBODY = "File not found after 3 retries.";

    /**
     * The file extension for HLS playlist.
     */
    private static final String M3U8 = ".m3u8";

    /**
     * Default output folder, configured in lambda.
     */
    private static final String DEFAULT_HLS = "/Default/HLS/";

    @Autowired
    private LessonsRepository repository;

    @Autowired
    private ModulesRepository modulesRepository;

    @Autowired
    private ChaptersRepository chaptersRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AwsConfig awsConfig;

    public List<LessonDTO> getLessonsByChapter(Long chapterId) {
        return repository.findByParentChapter(chapterId).stream().map(lesson -> mapper.map(lesson, LessonDTO.class))
                .collect(Collectors.toList());
    }

    public List<LessonDTO> getAllLessons() {
        List<Lessons> list = (List<Lessons>) repository.findAll();
        return list.stream().map(lesson -> mapper.map(lesson, LessonDTO.class)).collect(Collectors.toList());
    }

    /**
     * Adds the file details in table based on file name passed. Searches the output folder and retries 3 times (incremental 15s - 30s - 45s)
     * ADDS - module - chapters - lessons.
     */
    @Override
    @Async
    public void addFileDetails(String fileName) {

        AWSCredentials credentials = new BasicAWSCredentials(awsConfig.getAccessKey(), awsConfig.getSecret());
        if (!StringUtils.isEmpty(fileName)) {
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1)
                    .build();
            // RETRY logic
            int counter = 0;
            int sleepms = 15000;
            while (counter <= maxRetries) {
                log.info("checking for output file {} ", fileName);
                if (s3client.doesObjectExist(awsConfig.getOutputBucket(), fileName + DEFAULT_HLS + fileName + M3U8)) {

                    processFile(fileName, s3client);
                    break;
                } else {
                    if (counter == maxRetries) {
                        // send mail
                        sendMailNotification(fileName, credentials);
                        break;
                    }
                    log.info("No output file generated for {} yet..", fileName);
                    sleepms *= (counter + 1);
                    log.info("retry # {} , sleeping for {}", counter + 1, sleepms);
                    try {
                        Thread.sleep(sleepms);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                counter++;
            }
        }
    }

    /**
     * Sends EMAIL using AWS SES.
     * @param fileName
     * @param credentials
     */
    private void sendMailNotification(String fileName, AWSCredentials credentials) {
        try {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1).withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();
            SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(awsConfig.getMailTo()))
                    .withMessage(new com.amazonaws.services.simpleemail.model.Message()
                            .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(HTMLBODY))
                                    .withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
                            .withSubject(new Content().withCharset("UTF-8").withData(fileName + "-" + SUBJECT)))
                    .withSource(awsConfig.getMailFrom());

            client.sendEmail(request);
            log.info("Email sent!");
        } catch (Exception ex) {
            log.error("The email was not sent. Error message: {}", ex.getMessage());
        }
    }

    /**
     * Checks S3 for JSON object - parses and saves to table.
     * @param fileName
     * @param s3client
     */
    private void processFile(String fileName, AmazonS3 s3client) {
        log.info("looking for file {} ", "inputs/" + fileName + ".json");
        S3Object s3object = s3client.getObject(awsConfig.getBucket(), "inputs/" + fileName + ".json");
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Hierarchy obj = mapper.readValue(inputStream, Hierarchy.class);
            if (obj != null) {
                List<Modules> listOfModules = modulesRepository.findByModuleName(obj.getModule().getName());
                if (listOfModules != null && !listOfModules.isEmpty()) {
                    Modules module = listOfModules.stream().findFirst().get();
                    // check if chapter available.
                    Chapters chapter = checkIfChapterExistsElseAdd(module, obj);
                    addLesson(chapter, obj, fileName);

                } else {
                    Modules module = new Modules();
                    module.setDescription(obj.getModule().getDescription());
                    module.setModuleName(obj.getModule().getName());
                    modulesRepository.save(module);
                    Chapters chapter = checkIfChapterExistsElseAdd(module, obj);
                    addLesson(chapter, obj, fileName);
                }
            }
        } catch (IOException e) {
            log.error("Exception while parsing and loading the file to table {}", e.getMessage());
        }
    }

    /**
     * Deals with lessons.
     * @param chapter
     * @param obj
     * @param fileName
     */
    private void addLesson(Chapters chapter, Hierarchy obj, String fileName) {
        Lessons lesson = new Lessons();
        lesson.setParentChapter(chapter);
        lesson.setDescription(obj.getModule().getChapter().getLesson().getDesc());
        lesson.setLessonName(obj.getModule().getChapter().getLesson().getName());
        lesson.setUrl(awsConfig.getS3Domain() + awsConfig.getOutputBucket() + "/" + fileName + DEFAULT_HLS
                + fileName + M3U8);
        repository.save(lesson);
    }

    /**
     * Deals with chapters.
     * @param module
     * @param obj
     * @return
     */
    private Chapters checkIfChapterExistsElseAdd(Modules module, Hierarchy obj) {
        List<Chapters> listOfChapters = chaptersRepository.findByChapterName(obj.getModule().getChapter().getName());
        if (listOfChapters != null && !listOfChapters.isEmpty()) {
            // something exists don't do anything.
            return listOfChapters.stream().findFirst().get();
        } else {
            Chapters chapter = new Chapters();
            chapter.setChapterName(obj.getModule().getChapter().getName());
            chapter.setDescription(obj.getModule().getChapter().getDesc());
            chapter.setParentModule(module);
            return chaptersRepository.save(chapter);
        }
    }

}