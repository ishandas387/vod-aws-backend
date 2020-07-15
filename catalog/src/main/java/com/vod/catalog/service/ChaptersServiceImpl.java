package com.natyaguru.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import com.natyaguru.catalog.dto.ChapterDTO;
import com.natyaguru.catalog.repository.ChaptersRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChaptersServiceImpl implements IChaptersService {

    @Autowired
    private ChaptersRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ChapterDTO> getChaptersByModule(Long moduleId) {
        return repository.findByParentModuleId(moduleId).stream().map(chap -> mapper.map(chap, ChapterDTO.class))
                .collect(Collectors.toList());
    }

}