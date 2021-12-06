package com.example.choose.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choose.R;
import com.example.choose.api.PostController;
import com.example.choose.dto.GetFeedRequestDTO;
import com.example.choose.dto.GetFeedResponseDTO;
import com.example.choose.recycler.post.PostAdapter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostFragment extends Fragment{

    private PostAdapter adapter;
    private RecyclerView recyclerView;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new PostAdapter();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request builder = chain
                        .request()
                        .newBuilder()
                        .addHeader("Authorization", "basic dGVzdDp0ZXN0")
                        .build();
                return chain.proceed(builder);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://choose.teheidoma.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        postController
                .getFeed(new GetFeedRequestDTO(0,0,10))
                .enqueue(new Callback<GetFeedResponseDTO>() {
                    @Override
                    public void onResponse(Call<GetFeedResponseDTO> call, Response<GetFeedResponseDTO> response) {
                        if (response.isSuccessful()) {
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
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View inflate = inflater.inflate(R.layout.fragment_posts, container, false);
        recyclerView = inflate.findViewById(R.id.content_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return inflate;
    }
}
