package com.example.choose.dto;

import java.util.Objects;

public class PostDTO {
    private Long id;
    private String title;
    private String type;
    private String content;
    private String description;
    private String url;

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String type, String content, String description, String url) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.content = content;
        this.description = description;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
