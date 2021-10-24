package com.example.choose;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreatePost extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        FloatingActionButton btn2 = findViewById(R.id.fab_start);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePost.this, ChooseType.class));
            }
        });
    }




}