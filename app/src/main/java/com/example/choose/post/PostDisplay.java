package com.example.choose.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.choose.R;
import com.example.choose.community.CommunityDisplay;
import com.example.choose.create.PlayOffChooseFragment;
import com.example.choose.dto.ImagePostDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.dto.PostDTODeserializer;
import com.example.choose.dto.TextPostDTO;
import com.example.choose.home.CommunitiesFragment;
import com.example.choose.home.FeedFragment;
import com.example.choose.home.HomeActivity;
import com.example.choose.home.NotificationsFragment;
import com.example.choose.home.user.PersonalPageFragment;

public class PostDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_display);

        TextView title = findViewById(R.id.textView10);
        TextView name = findViewById(R.id.name);
        Button button = findViewById(R.id.sendBtn);

        Bundle extras = getIntent().getExtras();
        String from = extras.getString("from");


        PostDTO post = (PostDTO) extras.getSerializable("post");
        title.setText(post.getTitle());

        switch (post.getType()){
            case TEXT:
                TextPostDTO textPostDTO = ((TextPostDTO) post);
                displayFirstView(textPostDTO.getContent());
                break;
            case IMAGE:
                ImagePostDTO imagePostDTO = ((ImagePostDTO) post);
                name.setText("Image Post");
                displaySecondView(imagePostDTO.getDescription(), imagePostDTO.getUrl());
                break;
        }

        button.setOnClickListener(v -> {
            if(from.equals("CommunityDisplay")) {
                Intent i = new Intent(PostDisplay.this, CommunityDisplay.class);
                i.putExtra("community", extras.getSerializable("community"));
                startActivity(i);
            } else {
                startActivity(new Intent(PostDisplay.this, HomeActivity.class));
            }
        });
    }

    public void showFragment(Fragment fragment, int position) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.display_container, fragment, fragment.getClass().getName());
        mTransaction.commit();
    }

    public void displayFirstView(String desc) {
        showFragment(new TextPostDisplayFragment(desc),0);
    }

    public void displaySecondView(String desc, String url) {
        showFragment(new ImagePostDisplayFragment(desc, url),1);
    }
}