package com.vod.catalog.repository;

import java.util.List;

import com.natyaguru.catalog.entity.Modules;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModulesRepository extends CrudRepository<Modules,Integer> {
    
    @Override
    List<Modules> findAll();

    List<Modules> findByModuleName(String moduleName);
}