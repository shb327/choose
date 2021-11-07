package com.example.choose.service;

import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.PostDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostServiceApi {
    @POST("/api/posts")
    Call<Long> createPost(@Body CreatePostRequestDTO request);

    @GET("/api/posts")
    Call<List<PostDTO>> getAllPosts();
}
