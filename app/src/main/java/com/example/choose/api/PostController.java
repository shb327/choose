package com.example.choose.api;

import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.GetCommunityFeedRequestDTO;
import com.example.choose.dto.GetCommunityFeedResponseDTO;
import com.example.choose.dto.GetFeedRequestDTO;
import com.example.choose.dto.GetFeedResponseDTO;
import com.example.choose.dto.PostDTO;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PostController {
    @POST("/api/posts/create/text")
    Call<PostDTO> createTextPost(@Body CreatePostRequestDTO createPostRequestDTO);

    @Multipart
    @POST("/api/posts/create/image")
    Call<PostDTO> createImagePost(@Part @Query("title")String title,
                                  @Part @Query("description")String  description,
                                  @Part MultipartBody.Part file);

    @POST("/api/posts/feed")
    Call<GetFeedResponseDTO> getFeed (@Body GetFeedRequestDTO getFeedRequestDTO);

    @POST("/api/posts/feed/community")
    Call<GetCommunityFeedResponseDTO> getCommunityFeed (@Body GetCommunityFeedRequestDTO getCommunityFeedRequestDTO);
}
