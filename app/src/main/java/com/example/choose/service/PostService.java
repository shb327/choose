package com.example.choose.service;

import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.PostDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostService {
    private static PostService INSTANCE;
    private final static String BASE_URL = "http://10.0.2.2:8080";
    private final PostServiceApi api;
    private final Retrofit retrofit;

    private PostService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(PostServiceApi.class);
    }

    public static PostService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostService();
        }
        return INSTANCE;
    }

    public Call<Long> createPost(String id, String title) {
        return api.createPost(new CreatePostRequestDTO(id, title));
    }

    public Call<List<PostDTO>> getAllPosts() {
        return api.getAllPosts();
    }
}
