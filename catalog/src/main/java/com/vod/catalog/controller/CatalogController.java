package com.vod.catalog.controller;

import java.util.List;

import com.natyaguru.catalog.dto.ChapterDTO;
import com.natyaguru.catalog.dto.LessonDTO;
import com.natyaguru.catalog.dto.ModuleDTO;
import com.natyaguru.catalog.service.IChaptersService;
import com.natyaguru.catalog.service.ILessonsService;
import com.natyaguru.catalog.service.IModulesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/catalog")
public class CatalogController {
    
    @Autowired
    private ILessonsService lessonService;

    @Autowired
    private IChaptersService chapterService;

    @Autowired
    private IModulesService moduleService;
    

    @GetMapping("/heartbeat")
    public String alive(){
        return "heartbeat";
    }

    @GetMapping("/modules")
    public ResponseEntity<List<ModuleDTO>> getModules(){
        List<ModuleDTO> moduleList = moduleService.getAllModules();
        return new ResponseEntity<List<ModuleDTO>>(moduleList, HttpStatus.OK);
    }

    @GetMapping("/module/{moduleid}/chapters")
    public ResponseEntity<List<ChapterDTO>> getChaptersByModule(@PathVariable(name = "moduleid") Long moduleId){
        List<ChapterDTO> chapterList = chapterService.getChaptersByModule(moduleId);
        return new ResponseEntity<List<ChapterDTO>>(chapterList, HttpStatus.OK);
    }

    @GetMapping("chapter/{chapterid}/lessons")
    public ResponseEntity<List<LessonDTO>> getLessonsByChapterId(@PathVariable(value = "chapterid") Long chapterId){
        List<LessonDTO> lessons = lessonService.getLessonsByChapter(chapterId);
        return new ResponseEntity<List<LessonDTO>>(lessons, HttpStatus.OK);
    }

    @GetMapping("/lessons")
    public List<LessonDTO> getAll(){
        return lessonService.getAllLessons();
    }

    @PutMapping("/file/{filename}")
    public ResponseEntity<String> addFileDetails(@PathVariable("filename") String fileName){
        lessonService.addFileDetails(fileName);
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }
    

}