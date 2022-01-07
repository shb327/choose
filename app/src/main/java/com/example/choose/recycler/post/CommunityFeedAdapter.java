package com.example.choose.recycler.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.choose.R;
import com.example.choose.dto.ImagePostDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.dto.PostType;
import com.example.choose.dto.TextPostDTO;

import java.util.ArrayList;
import java.util.List;

public class CommunityFeedAdapter extends RecyclerView.Adapter<CommunityFeedAdapter.ViewHolder>{
    public List<PostDTO> localDataSet = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
        }
        public TextView getDescription() { return description; }
        public TextView getTitle() {
            return title;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        PostDTO post = localDataSet.get(position);
        PostType type = post.getType();
        viewHolder.getTitle().setText(post.getTitle());
        switch (type){
            case IMAGE:
                ImagePostDTO imagePostDTO = ((ImagePostDTO) post);
                viewHolder.getDescription().setText(imagePostDTO.getDescription());
                break;
            default:
            case TEXT:
                TextPostDTO textPostDTO = ((TextPostDTO) post);
                viewHolder.getDescription().setText(textPostDTO.getContent());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
