package com.example.choose.dto;

public class ImagePostDTO extends PostDTO {
    private String url;
    private String description;

    public ImagePostDTO(Long id, String title, PostType type, String url, String description) {
        super(id, title, type);
        this.url = url;
        this.description = description;
    }

    public ImagePostDTO() { }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
