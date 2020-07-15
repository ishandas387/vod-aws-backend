package com.vod.catalog.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws")
public class AwsConfig {

    private static final Logger log = LoggerFactory.getLogger(AwsConfig.class);
    
    private String accessKey;
    private String secret;
    private String bucket;
    private String outputBucket;
    private String mailTo;
    private String mailFrom;
    /**
     * S3 domain will change accordingly, if bucket changes or cloudfront
     * configured.
     */
    private String s3Domain;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getOutputBucket() {
        return outputBucket;
    }

    public void setOutputBucket(String outputBucket) {
        this.outputBucket = outputBucket;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getS3Domain() {
        return s3Domain;
    }

    public void setS3Domain(String s3Domain) {
        this.s3Domain = s3Domain;
    }

    @Override
    public String toString() {
        return "AwsConfig [accessKey=" + accessKey + ", bucket=" + bucket + ", mailFrom=" + mailFrom + ", mailTo="
                + mailTo + ", outputBucket=" + outputBucket + ", s3Domain=" + s3Domain + ", secret=" + secret + "]";
    }
    
    @PostConstruct
    public void postConstruct(){
        log.info("aws prop load {}", this.toString());
    }

}