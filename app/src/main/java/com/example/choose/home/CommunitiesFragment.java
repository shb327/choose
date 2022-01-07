package com.example.choose.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choose.R;
import com.example.choose.api.CommunityController;
import com.example.choose.community.CommunityDisplay;
import com.example.choose.dto.CommunityDTO;
import com.example.choose.recycler.post.CommunityAdapter;
import com.example.choose.recycler.post.RecyclerItemClickListener;
import com.example.choose.retrofit.RetrofitUtils;

import java.util.List;

public class CommunitiesFragment extends Fragment {

    private final CommunityAdapter adapter = new CommunityAdapter();
    private RecyclerView recyclerView;
    CommunityController communityController;

    int page;

    public CommunitiesFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = 0;
        communityController = RetrofitUtils
                .getInstance()
                .getRetrofit()
                .create(CommunityController.class);
        communityController
                .getAllCommunities(page++, 10)
                .enqueue(new Callback<List<CommunityDTO>>() {
                @Override
                public void onResponse(Call<List<CommunityDTO>> call, Response<List<CommunityDTO>> response) {
                    if (response.isSuccessful()) {
                        adapter.localDataSet.clear();
                        adapter.localDataSet.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("community", String.valueOf(response.code()));
                        Log.e("community", response.raw().toString());
                    }
                }

                @Override
                public void onFailure(Call<List<CommunityDTO>> call, Throwable t) {
                    Log.e("community", t.getMessage(), t);
                }
            });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_communities, container, false);
        recyclerView = inflate.findViewById(R.id.communities_recycle_view);
        SearchView searchView = inflate.findViewById(R.id.searchView);
        searchView.clearFocus();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(inflate.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(inflate.getContext(), CommunityDisplay.class);
                        i.putExtra("community", adapter.localDataSet.get(position));
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

