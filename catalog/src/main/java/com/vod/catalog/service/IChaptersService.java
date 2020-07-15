package com.natyaguru.catalog.service;

import java.util.List;

import com.natyaguru.catalog.dto.ChapterDTO;

public interface IChaptersService {

	List<ChapterDTO> getChaptersByModule(Long moduleId);
    
}