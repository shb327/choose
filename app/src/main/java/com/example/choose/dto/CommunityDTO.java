package com.example.choose.dto;

public class CommunityDTO {
    private Long id;
    private String name;
    private String username;
    private String description;
    private Long owner;

    public CommunityDTO() { }

    public CommunityDTO(Long id, String name, String username, String description, Long owner) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.description = description;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "CommunityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                '}';
    }
}
