package com.example.choose.api;

import com.example.choose.dto.GetAllCommunitiesRequestDTO;
import com.example.choose.dto.GetAllCommunitiesResponseDTO;
import com.example.choose.dto.GetFeedRequestDTO;
import com.example.choose.dto.GetFeedResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CommunityController {

    @POST("/api/communities")
    Call<GetAllCommunitiesResponseDTO> getAllCommunities(@Body GetAllCommunitiesRequestDTO getAllCommunitiesRequestDTO);

}
