package com.example.choose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.choose.api.PostController;
import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.PostDTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TextPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_post);

        Button button = findViewById(R.id.sendBtn);
        EditText editText1 = findViewById(R.id.titleTxt);
        EditText editText2 = findViewById(R.id.contentTxt);

        FloatingActionButton btn2 = findViewById(R.id.fab_start);
        btn2.setOnClickListener(v -> startActivity(new Intent(TextPost.this, ChooseType.class)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        MenuItem item = bottomNavigationView.getMenu().findItem(R.id.profile);
        PostController postController = RetrofitUtils.getInstance().getRetrofit().create(PostController.class);
        item.setOnMenuItemClickListener(item1 -> {
            startActivity(new Intent(TextPost.this, CreatePost.class));
            return false;
        });

        button.setOnClickListener(v ->
                postController.createPost(new CreatePostRequestDTO(
                                                                    editText2.getText().toString(),
                                                                    editText1.getText().toString()))
                        .enqueue(new Callback<PostDTO>() {
                            @Override
                            public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                                startActivity(new Intent(TextPost.this, CreatePost.class));
                            }

                            @Override
                            public void onFailure(Call<PostDTO> call, Throwable t) {
                                Toast.makeText(TextPost.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }));
    }
}