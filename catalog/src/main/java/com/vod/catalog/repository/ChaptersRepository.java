package com.vod.catalog.repository;

import java.util.List;

import com.natyaguru.catalog.entity.Chapters;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChaptersRepository extends CrudRepository<Chapters,Long> {
    List<Chapters> findByParentModuleId(Long moduleId);
    List<Chapters> findByChapterName(String chapterName);
}