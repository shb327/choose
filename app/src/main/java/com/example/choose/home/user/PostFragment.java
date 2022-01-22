package com.example.choose.home.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.choose.R;
import com.example.choose.dto.GetFeedRequestDTO;
import com.example.choose.post.PostDisplay;
import com.example.choose.recyclers.ClickListener;
import com.example.choose.retrofit.RetrofitUtils;
import com.example.choose.api.PostController;
import com.example.choose.dto.GetFeedResponseDTO;
import com.example.choose.recyclers.PostAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment{

    private final PostAdapter adapter = new PostAdapter(new ClickListener() {
        @Override
        public void onPositionClicked(int position) {
            Intent i = new Intent(getContext(), PostDisplay.class);
            i.putExtra("post", adapter.localDataSet.get(position));
            i.putExtra("from", "PostFragment");
            startActivity(i);
        }

        @Override
        public void onLongClicked(int position) {

        }
    });

    private RecyclerView recyclerView;
    PostController postController;

    int visibleItemCount;
    int totalItemCount;
    int pastVisibleItems;

    int page;

    private boolean loading;

    public PostFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postController = RetrofitUtils.getInstance().getRetrofit().create(PostController.class);
        loading = true;
        page = 0;
        postController
                .getSelfFeed(new GetFeedRequestDTO(page++,10))
                .enqueue(new Callback<GetFeedResponseDTO>() {
                    @Override
                    public void onResponse(Call<GetFeedResponseDTO> call, Response<GetFeedResponseDTO> response) {
                        if (response.isSuccessful()) {
                            adapter.localDataSet.clear();
                            adapter.localDataSet.addAll(response.body().getPosts());
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("post", String.valueOf(response.code()));
                            Log.e("post", response.raw().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<GetFeedResponseDTO> call, Throwable t) {
                        Log.e("post", t.getMessage(), t);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        recyclerView = view.findViewById(R.id.content_recycle_view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            postController
                                    .getSelfFeed(new GetFeedRequestDTO(page++,10))
                                    .enqueue(new Callback<GetFeedResponseDTO>() {
                                        @Override
                                        public void onResponse(Call<GetFeedResponseDTO> call, Response<GetFeedResponseDTO> response) {
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
                                        public void onFailure(Call<GetFeedResponseDTO> call, Throwable t) {
                                            Log.e("post", t.getMessage(), t);
                                        }
                                    }
                                    );
                        }
                    }
                }
            }
        });
        return view;
    }
}
