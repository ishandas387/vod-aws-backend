package com.vod.catalog.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MODULES")
public class Modules implements Serializable{
    
    
    /**
     *
     */
    private static final long serialVersionUID = 5255199515664626764L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "module_name" )
    @NotNull
    private String moduleName;

    @Column(name = "description")
    private String description;

/*    @OneToMany(mappedBy = "parentModule", fetch = FetchType.LAZY)
    private Set<Chapters> chapters; */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

/*     public Set<Chapters> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapters> chapters) {
        this.chapters = chapters;
    } */

    
}