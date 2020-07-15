package com.natyaguru.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import com.natyaguru.catalog.dto.ModuleDTO;
import com.natyaguru.catalog.repository.ModulesRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModulesServiceImpl implements IModulesService {

    @Autowired
    private ModulesRepository repository;

    @Autowired
    private ModelMapper mapper;
    
    @Override
    public List<ModuleDTO> getAllModules() {
        return repository.findAll().stream().map(mod -> mapper.map(mod, ModuleDTO.class))
        .collect(Collectors.toList());
    }
    
}