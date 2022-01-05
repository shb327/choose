package com.example.choose.api;

import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.GetFeedRequestDTO;
import com.example.choose.dto.GetFeedResponseDTO;
import com.example.choose.dto.PostDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostController {
    @POST("/api/posts/create/text")
    Call<PostDTO> createPost (@Body CreatePostRequestDTO createPostRequestDTO);

    @POST("/api/posts/feed")
    Call<GetFeedResponseDTO> getFeed (@Body GetFeedRequestDTO getFeedRequestDTO);
}
