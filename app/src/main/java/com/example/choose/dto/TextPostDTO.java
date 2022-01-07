package com.example.choose.dto;

public class TextPostDTO extends PostDTO{
    private String content;

    public TextPostDTO() { }

    public TextPostDTO(Long id, String title, PostType type, String content) {
        super(id, title, type);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
