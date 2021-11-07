package com.example.choose;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.choose.dto.PostDTO;
import com.example.choose.recycler.post.PostAdapter;
import com.example.choose.service.PostService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePost extends AppCompatActivity {

    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        FloatingActionButton btn2 = findViewById(R.id.fab_start);

        btn2.setOnClickListener(v -> startActivity(new Intent(CreatePost.this, ChooseType.class)));

        RecyclerView contentView = findViewById(R.id.content_recycle_view);
        contentView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter();
        contentView.setAdapter(adapter);
        AsyncTask.execute(() -> {
            PostService.getInstance()
                    .getAllPosts()
                    .enqueue(new Callback<List<PostDTO>>() {
                        @Override
                        public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                            if (response.isSuccessful()) {
                                adapter.localDataSet.addAll(response.body());
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PostDTO>> call, Throwable t) {
                            Log.e("post", t.getMessage(), t);
                        }
                    });
        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}