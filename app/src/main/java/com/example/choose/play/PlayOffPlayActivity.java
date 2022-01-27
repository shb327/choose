package com.example.choose.play;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choose.R;
import com.example.choose.api.PostController;
import com.example.choose.dto.CommunityDTO;
import com.example.choose.dto.GetFeedRequestDTO;
import com.example.choose.dto.GetFeedResponseDTO;
import com.example.choose.dto.PetitionPostDTO;
import com.example.choose.dto.PlayOffOptionDTO;
import com.example.choose.dto.PlayOffPostDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.post.DownloadImageTask;
import com.example.choose.post.PostDisplay;
import com.example.choose.recyclers.ClickListener;
import com.example.choose.recyclers.PlayOffAdapter;
import com.example.choose.retrofit.RetrofitUtils;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayOffPlayActivity extends AppCompatActivity {
    Integer id;
    String from;
    PostDTO post;
    TextView name;
    TextView optionOneName;
    TextView optionTwoName;
    ImageView optionOneImage;
    ImageView optionTwoImage;
    ImageView up;
    ImageView down;
    Random rand = new Random();
    PostController postController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_off_play);
        Bundle extras = getIntent().getExtras();

        name = findViewById(R.id.name);
        Button button = findViewById(R.id.back);
        optionOneName = findViewById(R.id.textOptionOne);
        optionTwoName = findViewById(R.id.textOptionTwo);
        optionOneImage = findViewById(R.id.imageOptionOne);
        optionTwoImage = findViewById(R.id.imageOptionTwo);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        id = extras.getInt("id");
        from = extras.getString("from");

        postController = RetrofitUtils
                .getInstance()
                .getRetrofit()
                .create(PostController.class);

        postController.getPost(id).enqueue(new Callback<PostDTO>() {
            @Override
            public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                post = response.body();

                List<PlayOffOptionDTO> start = new ArrayList<>();
                List<PlayOffOptionDTO> winners = new ArrayList<>();
                List<PlayOffOptionDTO> losers = new ArrayList<>();
                final boolean[] firstRoundFinished = {false};

                start.addAll(((PlayOffPostDTO) post).getOptions());

                //displayRoundName(start.size());
                List<Integer> optionPositions = getRandNumbers(start.size());

                List<Integer> onClickOptionPositions = new ArrayList<>();
                onClickOptionPositions = getRandNumbers(start.size());
                displayOptions(start.get(optionPositions.get(0)), start.get(optionPositions.get(1)));

//                up.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(!firstRoundFinished[0]){
//                            winners.add(start.get(optionPositions.get(0)));
//                            losers.add(start.get(optionPositions.get(1)));
//                            start.remove(start.get(optionPositions.get(1)));
//                            start.remove(start.get(optionPositions.get(0)));
//                        }else{
//                            winners.add(start.get(onClickOptionPositions.get(0)));
//                            losers.add(start.get(onClickOptionPositions.get(1)));
//                            start.remove(start.get(onClickOptionPositions.get(1)));
//                            start.remove(start.get(onClickOptionPositions.get(0)));
//                        }
//
//                        onClickOptionPositions = getRandNumbers(start.size());
//                        firstRoundFinished[0] = true;
//                        displayOptions(start.get(onClickOptionPositions.get(0)), start.get(onClickOptionPositions.get(1)));
//                    }
//                });

                down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //new DownloadImageTask(optionOneImage).execute(start.get(3).getMedia());
                    }
                });
            }

            @Override
            public void onFailure(Call<PostDTO> call, Throwable t) {
                Log.e("getPost", t.getMessage(), t);
            }
        });

        button.setOnClickListener(v -> {
            if(from.equals("CommunityDisplay")) {
                Intent i = new Intent(this, PostDisplay.class);
                i.putExtra("from", "CommunityDisplay");
                CommunityDTO communityDTO = (CommunityDTO) extras.getSerializable("community");
                i.putExtra("community", communityDTO);
                i.putExtra("post", post);
                startActivity(i);
            } else if(from.equals("CommunityDisplayCF")) {
                Intent i = new Intent(this, PostDisplay.class);
                i.putExtra("from", "CommunityDisplayCF");
                CommunityDTO communityDTO = (CommunityDTO) extras.getSerializable("community");
                i.putExtra("community", communityDTO);
                i.putExtra("post", post);
                startActivity(i);
            }else if(from.equals("Feed")) {
                Intent i = new Intent(this, PostDisplay.class);
                i.putExtra("from", "Feed");
                i.putExtra("post", post);
                startActivity(i);
            } else {
                Intent i = new Intent(this, PostDisplay.class);
                i.putExtra("from", "HomeActivity");
                i.putExtra("post", post);
                startActivity(i);
            }
        });
    }

    public void displayRoundName(int size){
        if(size > 2) name.setText("Play-Off   1/" + size/2);
        else name.setText("Play-Off   Finale");
    }

    public List<Integer> getRandNumbers(int size){
        int rand_int1 = 0;
        int rand_int2 = 0;
        while (rand_int1 == rand_int2){
            rand_int1 = rand.nextInt(size - 1);
            rand_int2 = rand.nextInt(size - 1);
        }
        return Arrays.asList(rand_int1, rand_int2);
    }

    public void displayOptions(PlayOffOptionDTO optionOne, PlayOffOptionDTO optionTwo){
        new DownloadImageTask(optionOneImage).execute(optionOne.getMedia());
        new DownloadImageTask(optionTwoImage).execute(optionTwo.getMedia());
        optionOneName.setText(optionOne.getTitle());
        optionTwoName.setText(optionTwo.getTitle());
    }
}