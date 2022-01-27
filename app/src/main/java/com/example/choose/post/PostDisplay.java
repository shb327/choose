package com.example.choose.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choose.R;
import com.example.choose.api.PostController;
import com.example.choose.community.CommunityDisplay;
import com.example.choose.dto.CommunityDTO;
import com.example.choose.dto.ImagePostDTO;
import com.example.choose.dto.LikeStatus;
import com.example.choose.dto.PetitionPostDTO;
import com.example.choose.dto.PlayOffPostDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.dto.TextPostDTO;
import com.example.choose.dto.VotingOptionDTO;
import com.example.choose.dto.VotingPostDTO;
import com.example.choose.home.HomeActivity;
import com.example.choose.retrofit.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDisplay extends AppCompatActivity {

    CommunityDTO communityDTO;
    PostController postController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_display);

        TextView title = findViewById(R.id.textView10);
        TextView name = findViewById(R.id.name);
        Button button = findViewById(R.id.sendBtn);
        TextView likeCount = findViewById(R.id.likeCount);
        ImageView like = findViewById(R.id.like);
        ImageView dislike = findViewById(R.id.dislike);

        Bundle extras = getIntent().getExtras();
        String from = extras.getString("from");

        PostDTO post = (PostDTO) extras.getSerializable("post");
        title.setText(post.getTitle());
        likeCount.setText(String.valueOf(post.getLikesCount().intValue()));

        button.setOnClickListener(v -> {
            if(from.equals("CommunityDisplay")) {
                Intent i = new Intent(PostDisplay.this, CommunityDisplay.class);
                i.putExtra("from", "DiscoverFragment");
                i.putExtra("community", (CommunityDTO) extras.getSerializable("community"));
                startActivity(i);
            } else if(from.equals("CommunityDisplayCF")) {
                Intent i = new Intent(PostDisplay.this, CommunityDisplay.class);
                i.putExtra("community", (CommunityDTO) extras.getSerializable("community"));
                i.putExtra("from", "CommunitiesFragment");
                startActivity(i);
            }else if(from.equals("Feed")) {
                Intent i = new Intent(PostDisplay.this, HomeActivity.class);
                i.putExtra("fragment", 3);
                startActivity(i);
            }else {
                startActivity(new Intent(PostDisplay.this, HomeActivity.class));
            }
        });

        if(post.getLikeStatus() != null){
            switch (post.getLikeStatus()){
                case LIKE:
                    likeCount.setTextColor(Color.parseColor("#205072"));
                    like.setImageResource(R.drawable.fnh);
                    break;
                case DISLIKE:
                    likeCount.setTextColor(Color.parseColor("#205072"));
                    dislike.setImageResource(R.drawable.fbh);
                    break;
            }
        }

        postController = RetrofitUtils.getInstance().getRetrofit().create(PostController.class);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(post.getLikeStatus() != null) {
                    if ((!post.getLikeStatus().equals(LikeStatus.LIKE)) || (post.getLikeStatus().equals(LikeStatus.DISLIKE))) {
                        postController.like(post.getId().intValue(), LikeStatus.LIKE).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                int tmp = Integer.parseInt(likeCount.getText().toString());
                                if(post.getLikeStatus() != null) {
                                    if (post.getLikeStatus().equals(LikeStatus.DISLIKE))
                                        tmp = tmp + 1;
                                }
                                tmp = tmp + 1;
                                likeCount.setText(String.valueOf(tmp));
                                likeCount.setTextColor(Color.parseColor("#205072"));
                                like.setImageResource(R.drawable.fnh);
                                dislike.setImageResource(R.drawable.obh);
                                post.setLikeStatus(LikeStatus.LIKE);
                                Log.i("Like: " + post.getId().intValue(), response.raw().request().headers().toString());
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("Like Error", t.getMessage(), t);
                            }
                        });
                    }
                }else{
                    postController.like(post.getId().intValue(), LikeStatus.LIKE).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int tmp = Integer.parseInt(likeCount.getText().toString());
                            likeCount.setText(String.valueOf(tmp + 1));
                            likeCount.setTextColor(Color.parseColor("#205072"));
                            like.setImageResource(R.drawable.fnh);
                            post.setLikeStatus(LikeStatus.LIKE);
                            Log.i("Like: " + post.getId().intValue(), response.raw().request().headers().toString());
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("Like Error", t.getMessage(), t);
                        }
                    });
                }
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(post.getLikeStatus() != null) {
                    if ((!post.getLikeStatus().equals(LikeStatus.DISLIKE)) || (post.getLikeStatus().equals(LikeStatus.LIKE))) {
                        postController.like(post.getId().intValue(), LikeStatus.DISLIKE).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                int tmp = Integer.parseInt(likeCount.getText().toString());
                                if(post.getLikeStatus() != null) {
                                    if (post.getLikeStatus().equals(LikeStatus.LIKE))
                                        tmp = tmp - 1;
                                }
                                tmp = tmp - 1;
                                likeCount.setText(String.valueOf(tmp));
                                likeCount.setTextColor(Color.parseColor("#205072"));
                                dislike.setImageResource(R.drawable.fbh);
                                like.setImageResource(R.drawable.onh);
                                post.setLikeStatus(LikeStatus.DISLIKE);
                                Log.i("Dislike: " + post.getId().intValue(), response.raw().request().headers().toString());
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("Dislike Error", t.getMessage(), t);
                            }
                        });
                    }
                }else{
                    postController.like(post.getId().intValue(), LikeStatus.DISLIKE).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int tmp = Integer.parseInt(likeCount.getText().toString());
                            likeCount.setText(String.valueOf(tmp - 1));
                            likeCount.setTextColor(Color.parseColor("#205072"));
                            dislike.setImageResource(R.drawable.fbh);
                            post.setLikeStatus(LikeStatus.LIKE);
                            Log.i("Dislike: " + post.getId().intValue(), response.raw().request().headers().toString());
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("Dislike Error", t.getMessage(), t);
                        }
                    });
                }
            }
        });

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
            case PETITION:
                PetitionPostDTO petitionPostDTO = ((PetitionPostDTO) post);
                name.setText("Petition Post");
                displayThirdView(petitionPostDTO.getId().intValue(),
                        petitionPostDTO.getDescription(),
                        petitionPostDTO.getMediaUrl());
                break;
            case VOTE:
                VotingPostDTO votingPostDTO = ((VotingPostDTO) post);
                name.setText("Voting Post");
                displayForthView(votingPostDTO.getId().intValue());
                break;
            case PLAYOFF:
                PlayOffPostDTO playOffPostDTO = ((PlayOffPostDTO) post);
                name.setText("Play-Off Post");
                communityDTO = ((CommunityDTO) extras.getSerializable("community"));
                if(communityDTO != null) displayFifthView(playOffPostDTO.getId().intValue(), from, communityDTO);
                else displayFifthView(playOffPostDTO.getId().intValue(), from);
                break;
        }
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.display_container, fragment, fragment.getClass().getName());
        mTransaction.commit();
    }

    public void displayFirstView(String desc) {
        showFragment(new TextPostDisplayFragment(desc));
    }

    public void displaySecondView(String desc, String url) {
        showFragment(new ImagePostDisplayFragment(desc, url));
    }

    public void displayThirdView(Integer id, String desc, String url) {
        showFragment(new PetitionPostDisplayFragment(id, desc, url));
    }

    public void displayForthView(Integer id) {
        showFragment(new VotingPostDisplayFragment(id));
    }

    public void displayFifthView(Integer id, String from) {
        showFragment(new PlayOffPostDisplayFragment(id, from));
    }

    public void displayFifthView(Integer id, String from, CommunityDTO communityDTO) {
        showFragment(new PlayOffPostDisplayFragment(id, from, communityDTO));
    }
}