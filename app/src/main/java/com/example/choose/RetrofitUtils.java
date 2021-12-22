package com.example.choose;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.choose.api.PostController;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static final RetrofitUtils INSTANCE = new RetrofitUtils();
    private Retrofit retrofit;
    private String login;
    private String password;


    private RetrofitUtils() {
    }

    public static RetrofitUtils getInstance() {
        return INSTANCE;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            createRetrofit(null);
        }
        return retrofit;
    }

    public void createRetrofit(String session) {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://choose.teheidoma.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .followRedirects(false)
                        .addInterceptor(chain -> {
                            Request.Builder builder = chain
                                    .request()
                                    .newBuilder();
                            if (login != null && password != null) {
                                builder.addHeader("Authorization", getAuthHeader());
                            }
                            return chain.proceed(builder.build());
                        }).build())
                .build();
    }

    @SuppressLint("NewApi")
    private String getAuthHeader() {
        String value = login + ":" + password;
        return "Basic "+ Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.US_ASCII));
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
