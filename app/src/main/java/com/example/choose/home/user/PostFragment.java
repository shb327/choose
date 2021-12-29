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
import com.example.choose.RecyclerItemClickListener;
import com.example.choose.RetrofitUtils;
import com.example.choose.PostDisplay;
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

    public PostFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postController = RetrofitUtils.getInstance().getRetrofit().create(PostController.class);
        
        postController
                .getFeed(new GetFeedRequestDTO(0,0,10))
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(inflate.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(inflate.getContext(), PostDisplay.class);
                        i.putExtra("title", adapter.localDataSet.get(position).getTitle());
                        i.putExtra("desc", adapter.localDataSet.get(position).getContent());
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        //TODO: Options
                    }
                })
        );
        return inflate;
    }
}
