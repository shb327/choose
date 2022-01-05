package com.example.choose.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choose.R;
import com.example.choose.api.CommunityController;
import com.example.choose.dto.GetAllCommunitiesRequestDTO;
import com.example.choose.dto.GetAllCommunitiesResponseDTO;
import com.example.choose.recycler.post.CommunityAdapter;
import com.example.choose.retrofit.RetrofitUtils;

public class CommunitiesFragment extends Fragment {

    private final CommunityAdapter adapter = new CommunityAdapter();
    private RecyclerView recyclerView;
    CommunityController communityController;

    public CommunitiesFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        communityController = RetrofitUtils
                .getInstance()
                .getRetrofit()
                .create(CommunityController.class);
        communityController
                .getAllCommunities(new GetAllCommunitiesRequestDTO())
                .enqueue(new Callback<GetAllCommunitiesResponseDTO>() {
                    @Override
                    public void onResponse(Call<GetAllCommunitiesResponseDTO> call, Response<GetAllCommunitiesResponseDTO> response) {
                        if (response.isSuccessful()) {
                            adapter.localDataSet.clear();
                            adapter.localDataSet.addAll(response.body().getCommunities());
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("community", String.valueOf(response.code()));
                            Log.e("community", response.raw().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAllCommunitiesResponseDTO> call, Throwable t) {
                        Log.e("community", t.getMessage(), t);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_communities, container, false);
        recyclerView = inflate.findViewById(R.id.communities_recycle_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return inflate;
    }
}

