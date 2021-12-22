package com.example.choose.api;

import com.example.choose.dto.GetFeedRequestDTO;
import com.example.choose.dto.RegistrationUsernameDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RegistrationController {
    @POST("/api/registration/username")
    Call<Void> username(@Body RegistrationUsernameDTO registrationUsernameDTO);
}
