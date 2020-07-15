
package com.vod.catalog.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "desc",
    "chapter"
})
public class Module {

    @JsonProperty("name")
    @NotNull
    private String name;
    @JsonProperty("desc")
    @NotNull
    private String description;
    @JsonProperty("chapter")
    @NotNull
    private Chapter chapter;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("desc")
    public String getDescription() {
        return description;
    }

    @JsonProperty("desc")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("chapter")
    public Chapter getChapter() {
        return chapter;
    }

    @JsonProperty("chapter")
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
