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


@Entity
@Table(name = "lessons")
public class Lessons implements Serializable{

    /**
     * Entity for lessons table. 
     * NOTE: The table and column names are case sensitive in case of HIBERNATE connecting to POSTGRES
     * In case a postgres table is named 'LESSONS' @Table name = 'LESSONS' @ top of this entity will not 
     * match. Hibernate by default converts the name to lower cases. 
     */
    private static final long serialVersionUID = 9190614908155308484L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "lesson")
    private String lessonName;
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="chapter_id")
    private Chapters parentChapter;

    @Column(name = "url")
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Chapters getParentChapter() {
        return parentChapter;
    }

    public void setParentChapter(Chapters parentChapter) {
        this.parentChapter = parentChapter;
    }

    
    
}