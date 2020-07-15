package com.vod.catalog.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "chapters")
public class Chapters implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 169766630806608870L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "chapter")
    @NotNull
    private String chapterName;

    @Column(name = "description")
    private String description;
/* 
    @OneToMany(mappedBy = "parentChapter" , fetch = FetchType.LAZY)
    private Set<Lessons> lessons; */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Modules parentModule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }


/* 
    public Set<Lessons> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lessons> lessons) {
        this.lessons = lessons;
    } */

    public Modules getParentModule() {
        return parentModule;
    }

    public void setParentModule(Modules parentModule) {
        this.parentModule = parentModule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}