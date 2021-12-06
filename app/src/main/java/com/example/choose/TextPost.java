package com.example.choose;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.choose.api.PostController;
import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.PostDTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TextPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_post2);

        Button button = findViewById(R.id.sendBtn);
        EditText editText1 = findViewById(R.id.titleTxt);
        EditText editText2 = findViewById(R.id.contentTxt);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        MenuItem item = bottomNavigationView.getMenu().findItem(R.id.profile);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request builder = chain
                        .request()
                        .newBuilder()
                        .addHeader("Authorization", "basic dGVzdDp0ZXN0")
                        .build();
                return chain.proceed(builder);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://choose.teheidoma.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();

        PostController postController = retrofit.create(PostController.class);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(TextPost.this, CreatePost.class));
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postController.createPost(
                        new CreatePostRequestDTO(
                                editText2.getText().toString(),editText1.getText().toString()))
                        .enqueue(new Callback<PostDTO>() {
                            @Override
                            public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                                startActivity(new Intent(TextPost.this, CreatePost.class));
                            }

                            @Override
                            public void onFailure(Call<PostDTO> call, Throwable t) {
                                Toast.makeText(TextPost.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}