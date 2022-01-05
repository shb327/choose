package com.example.choose.recycler.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.choose.R;
import com.example.choose.dto.CommunityDTO;

import java.util.ArrayList;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder>{

    public List<CommunityDTO> localDataSet = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.community_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        CommunityDTO communityDTO = localDataSet.get(position);
        viewHolder.getName().setText(communityDTO.getName());
        viewHolder.getUsername().setText(communityDTO.getUsername());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView username;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            username = view.findViewById(R.id.username);
        }

        public TextView getName() {
            return name;
        }

        public TextView getUsername() {
            return username;
        }
    }
}
