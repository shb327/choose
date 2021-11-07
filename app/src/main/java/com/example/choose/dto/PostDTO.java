package com.example.choose.dto;

import java.util.Objects;

public class PostDTO {
    private Long id;
    private String title;

    public PostDTO(Long id, String title) {
        this.id = id;
        this.title = title;
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
