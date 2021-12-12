package com.example.choose.dto;

import java.util.Objects;

public class PostDTO {
    private Long id;
    private String title;
    private String type;

    public PostDTO(Long id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public PostDTO() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
