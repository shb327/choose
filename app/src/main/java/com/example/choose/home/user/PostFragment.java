package com.example.choose.home.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.choose.R;
import com.example.choose.post.PostDisplay;
import com.example.choose.recycler.post.RecyclerItemClickListener;
import com.example.choose.retrofit.RetrofitUtils;
import com.example.choose.api.PostController;
import com.example.choose.dto.GetFeedRequestDTO;
import com.example.choose.dto.GetFeedResponseDTO;
import com.example.choose.recycler.post.PostAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment{

    private final PostAdapter adapter = new PostAdapter();
    private RecyclerView recyclerView;
    PostController postController;

    int visibleItemCount;
    int totalItemCount;
    int pastVisiblesItems;

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
                .getFeed(new GetFeedRequestDTO(page++,10))
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
        View inflate = inflater.inflate(R.layout.fragment_posts, container, false);
        recyclerView = inflate.findViewById(R.id.content_recycle_view);

        TextView welcome = inflate.findViewById(R.id.welcome);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(inflate.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(inflate.getContext(), PostDisplay.class);
                        i.putExtra("title", adapter.localDataSet.get(position).getTitle());
                        i.putExtra("cont", adapter.localDataSet.get(position).getContent());
                        i.putExtra("type", adapter.localDataSet.get(position).getType());
                        i.putExtra("id", adapter.localDataSet.get(position).getId());
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
                                    .getFeed(new GetFeedRequestDTO(page++,10))
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

        return inflate;
    }
}
