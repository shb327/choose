package com.example.choose.dto;

import java.util.List;

public class GetAllCommunitiesResponseDTO {
    private List<CommunityDTO> communities;

    public List<CommunityDTO> getCommunities() {
        return communities;
    }

    public void setCommunities(List<CommunityDTO> communities) {
        this.communities = communities;
    }
}
