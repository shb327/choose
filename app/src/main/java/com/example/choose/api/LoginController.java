package com.example.choose.api;

import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.PostDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginController {
    @POST("/login")
    Call<Void> login(@Query("username") String username, @Query("password") String password);
}
