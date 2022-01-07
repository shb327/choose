package com.example.choose.api;

import com.example.choose.dto.CommunityDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommunityController {
    @GET("/api/communities")
    Call<List<CommunityDTO>> getAllCommunities(@Query("page") int page, @Query("size") int size);

}
