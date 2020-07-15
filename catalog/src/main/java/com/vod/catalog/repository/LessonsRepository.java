package com.vod.catalog.repository;

import java.util.List;

import com.natyaguru.catalog.entity.Lessons;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonsRepository extends CrudRepository<Lessons,Long> {
    List<Lessons> findByParentChapter(Long chapterId);
    List<Lessons> findByLessonName(String lessonName);
}