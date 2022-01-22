package com.example.choose.dto;

import java.io.Serializable;

public class PostDTO implements Serializable {
    private Long id;
    private Long authorId;
    private String authorUsername;
    private String title;
    private PostType type;

    public PostDTO() { }

    public PostDTO(Long id, Long authorId, String authorUsername, String title, PostType type) {
        this.id = id;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.title = title;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", authorUsername='" + authorUsername + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                '}';
    }
}
