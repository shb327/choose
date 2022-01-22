package com.example.choose.dto;

import java.util.List;
import java.util.Set;

public class VotingPostDTO extends PostDTO{
    private List<VotingOptionDTO> options;

    public VotingPostDTO() { }

    public VotingPostDTO(Long id, Long authorId, String authorUsername, String title, PostType type, List<VotingOptionDTO> options) {
        super(id, authorId, authorUsername, title, type);
        this.options = options;
    }

    public VotingPostDTO(Long id, Long authorId, String authorUsername, String title, PostType type) {
        super(id, authorId, authorUsername, title, type);
    }

    public List<VotingOptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<VotingOptionDTO> options) {
        this.options = options;
    }
}
