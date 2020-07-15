package com.natyaguru.catalog.service;

import java.util.List;

import com.natyaguru.catalog.dto.LessonDTO;

public interface ILessonsService {

	List<LessonDTO> getLessonsByChapter(Long chapterId);

	List<LessonDTO> getAllLessons();

	void addFileDetails(String fileName);

}