package com.example.choose.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.choose.R;
import com.example.choose.api.PostController;
import com.example.choose.dto.CommunityDTO;
import com.example.choose.dto.GetCommunityFeedRequestDTO;
import com.example.choose.dto.GetCommunityFeedResponseDTO;
import com.example.choose.dto.GetFeedRequestDTO;
import com.example.choose.dto.GetFeedResponseDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.post.PostDisplay;
import com.example.choose.recycler.post.CommunityFeedAdapter;
import com.example.choose.recycler.post.RecyclerItemClickListener;
import com.example.choose.retrofit.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityDisplay extends AppCompatActivity {
    PostController postController;
    CommunityFeedAdapter adapter = new CommunityFeedAdapter();
    private RecyclerView recyclerView;

    int page;
    private boolean loading;
    int visibleItemCount;
    int totalItemCount;
    int pastVisiblesItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_display);

        TextView name = findViewById(R.id.name);
        TextView username = findViewById(R.id.username);
        recyclerView = findViewById(R.id.communities_recycle_view);

        Bundle extras = getIntent().getExtras();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        CommunityDTO community = (CommunityDTO) extras.getSerializable("community");

        name.setText(community.getName());
        username.setText(community.getUsername());

        postController = RetrofitUtils.getInstance().getRetrofit().create(PostController.class);
        loading = true;
        page = 0;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

        postController
                .getCommunityFeed(new GetCommunityFeedRequestDTO(page++, 10, community.getId().intValue()))
                .enqueue(new Callback<GetCommunityFeedResponseDTO>() {
                    @Override
                    public void onResponse(Call<GetCommunityFeedResponseDTO> call, Response<GetCommunityFeedResponseDTO> response) {
                        if (response.isSuccessful()) {
                            adapter.localDataSet.addAll(response.body().getPosts());
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("post", String.valueOf(response.code()));
                            Log.e("post", response.raw().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCommunityFeedResponseDTO> call, Throwable t) {
                        Log.e("post", t.getMessage(), t);
                    }
                });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(CommunityDisplay.this, PostDisplay.class);
                        i.putExtra("post", adapter.localDataSet.get(position));
                        i.putExtra("from", "CommunityDisplay");
                        i.putExtra("community", community);
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        //TODO: Options
                    }
                })
        );

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            postController
                                    .getCommunityFeed(new GetCommunityFeedRequestDTO(page++,10, community.getId().intValue()))
                                    .enqueue(new Callback<GetCommunityFeedResponseDTO>() {
                                                 @Override
                                                 public void onResponse(Call<GetCommunityFeedResponseDTO> call, Response<GetCommunityFeedResponseDTO> response) {
                                                     if (response.isSuccessful()) {
                                                         adapter.localDataSet.addAll(response.body().getPosts());
                                                         adapter.notifyDataSetChanged();
                                                         loading = true;
                                                     } else {
                                                         Log.e("post", String.valueOf(response.code()));
                                                         Log.e("post", response.raw().toString());
                                                     }
                                                 }

                                                 @Override
                                                 public void onFailure(Call<GetCommunityFeedResponseDTO> call, Throwable t) {
                                                     Log.e("post", t.getMessage(), t);
                                                 }
                                             }
                                    );
                        }
                    }
                }
            }
        });

    }
}