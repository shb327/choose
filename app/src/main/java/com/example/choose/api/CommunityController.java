package com.example.choose.api;

import com.example.choose.dto.CommunityDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommunityController {
    @GET("/api/communities")
    Call<List<CommunityDTO>> getAllCommunities(@Query("page") int page, @Query("size") int size);

    @GET("/api/communities/user/all")
    Call<List<CommunityDTO>> getUserCommunities();

    @GET("/api/communities/discover")
    Call<List<CommunityDTO>> getDiscoverCommunity(@Query("page") int page, @Query("size") int size);

    @POST("/api/communities/post/add")
    Call<Void> addPost (@Query("communityId") int communityId, @Query("postId") int postId);

    @POST("/api/communities/join")
    Call<Void> joinCommunity (@Query("communityId") int communityId);

    @POST("/api/communities/leave")
    Call<Void> leaveCommunity (@Query("communityId") int communityId);
}
