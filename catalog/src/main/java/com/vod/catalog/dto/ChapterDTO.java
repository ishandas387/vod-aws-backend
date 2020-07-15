package com.vod.catalog.dto;

public class ChapterDTO {

    private Long id;
    private String chapterName;
    private String desc;
    //private ModuleDTO parentModule;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

/*     public ModuleDTO getParentModule() {
        return parentModule;
    }

    public void setParentModule(ModuleDTO parentModule) {
        this.parentModule = parentModule;
    } */

}