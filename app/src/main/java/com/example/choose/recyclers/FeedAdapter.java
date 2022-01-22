package com.example.choose.recyclers;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choose.R;
import com.example.choose.api.PostController;
import com.example.choose.dto.ImagePostDTO;
import com.example.choose.dto.PetitionPostDTO;
import com.example.choose.dto.PlayOffPostDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.dto.PostType;
import com.example.choose.dto.TextPostDTO;
import com.example.choose.dto.VotingOptionDTO;
import com.example.choose.dto.VotingPostDTO;
import com.example.choose.post.DownloadImageTask;
import com.example.choose.retrofit.RetrofitUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public List<PostDTO> localDataSet = new ArrayList<>();
    private final ClickListener listener;
    PostController postController;
    Activity activity;

    public FeedAdapter(ClickListener listener, Activity activity) {
        this.listener = listener;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
            case 1:
                View textView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.text_post_feed_row_item, parent, false);
                return new TextPostViewHolder(textView, listener);
            case 2:
                View imageView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.image_post_feed_row_item, parent, false);
                return new ImagePostViewHolder(imageView, listener);
            case 3:
                View petitionView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.petition_post_feed_row_item, parent, false);
                return new PetitionPostViewHolder(petitionView, listener);
            case 4:
                View votingView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.voting_post_feed_row_item, parent, false);
                return new VotingPostViewHolder(votingView, listener);
            case 5:
                View playOffView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.play_off_post_feed_row_item, parent, false);
                return new PlayOffPostViewHolder(playOffView, listener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        PostDTO post = localDataSet.get(position);
        PostType type = post.getType();
        switch (type){
            default:
            case TEXT:
                return 1;
            case IMAGE:
                return 2;
            case PETITION:
                return 3;
            case VOTE:
                return 4;
            case PLAYOFF:
                return 5;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        PostDTO post = localDataSet.get(position);
        switch (holder.getItemViewType()) {
            default:
            case 1:
                TextPostViewHolder textPostViewHolder = ((TextPostViewHolder) holder);
                textPostViewHolder.getTitle().setText(post.getTitle());
                textPostViewHolder.getAuthor().setText("by " + post.getAuthorUsername());
                TextPostDTO textPostDTO = ((TextPostDTO) post);
                textPostViewHolder.getDescription().setText(textPostDTO.getContent());
                break;
            case 2:
                ImagePostViewHolder imagePostViewHolder = ((ImagePostViewHolder) holder);
                imagePostViewHolder.getTitle().setText(post.getTitle());
                imagePostViewHolder.getAuthor().setText("by " + post.getAuthorUsername());
                ImagePostDTO imagePostDTO = ((ImagePostDTO) post);
                imagePostViewHolder.getDescription().setText(imagePostDTO.getDescription());
                new DownloadImageTask(imagePostViewHolder.getImage()).execute(imagePostDTO.getUrl());
                break;
            case 3:
                PetitionPostViewHolder petitionPostViewHolder = ((PetitionPostViewHolder) holder);
                petitionPostViewHolder.getTitle().setText(post.getTitle());
                petitionPostViewHolder.getAuthor().setText("by " + post.getAuthorUsername());
                PetitionPostDTO petitionPostDTO = ((PetitionPostDTO) post);
                petitionPostViewHolder.getDescription().setText(petitionPostDTO.getDescription());
                petitionPostViewHolder.setPostDTO(post);
                if(petitionPostDTO.getMediaUrl() != null) {
                    new DownloadImageTask(petitionPostViewHolder.getImage()).execute(petitionPostDTO.getMediaUrl());
                }else{
                    petitionPostViewHolder.getImage().setVisibility(View.GONE);
                }
                if(petitionPostDTO.getVoted()){
                    petitionPostViewHolder.getButton().setText("signed");
                    petitionPostViewHolder.getButton().setBackgroundColor(Color.parseColor("#329D9C"));
                    petitionPostViewHolder.getButton().setTextColor(Color.WHITE);
                    petitionPostViewHolder.getButton().setClickable(false);
                }
                break;
            case 4:
                VotingPostViewHolder votingPostViewHolder = ((VotingPostViewHolder) holder);
                votingPostViewHolder.getTitle().setText(post.getTitle());
                votingPostViewHolder.getAuthor().setText("by " + post.getAuthorUsername());
                VotingAdapter adapter;
                postController = RetrofitUtils
                        .getInstance()
                        .getRetrofit()
                        .create(PostController.class);
                adapter = new VotingAdapter(activity, post.getId().intValue(), new Supplier<VotingPostDTO>() {
                    @Override
                    public VotingPostDTO get() {
                        postController = RetrofitUtils
                                .getInstance()
                                .getRetrofit()
                                .create(PostController.class);
                        try {
                            return ((VotingPostDTO) postController.getPost(post.getId().intValue()).execute().body());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
                votingPostViewHolder.getRecyclerView().setLayoutManager(votingPostViewHolder.mLayoutManager);
                votingPostViewHolder.getRecyclerView().setAdapter(adapter);
                postController
                        .getPost(post.getId().intValue()).enqueue(new Callback<PostDTO>() {
                    @Override
                    public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                        for (VotingOptionDTO votingOptionDTO: (((VotingPostDTO) response.body()).getOptions()))
                            adapter.overall += votingOptionDTO.getVotedUsers();
                        adapter.localDataSet.addAll(((VotingPostDTO) response.body()).getOptions());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<PostDTO> call, Throwable t) {
                        Log.e("getPost", t.getMessage(), t);
                    }
                });
                break;
            case 5:
                PlayOffPostViewHolder playOffPostViewHolder = ((PlayOffPostViewHolder) holder);
                playOffPostViewHolder.getTitle().setText(post.getTitle());
                playOffPostViewHolder.getAuthor().setText("by " + post.getAuthorUsername());
                PlayOffPostDTO playOffPostDTO = ((PlayOffPostDTO) post);
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

    public static class TextPostViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private WeakReference<ClickListener> listenerRef;
        private final TextView title;
        private final TextView author;
        private final TextView description;

        public TextPostViewHolder(View view, ClickListener listener) {
            super(view);
            listenerRef = new WeakReference<>(listener);
            author = view.findViewById(R.id.author);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            view.setOnClickListener(this);
        }
        public TextView getDescription() { return description; }
        public TextView getTitle() {
            return title;
        }
        public TextView getAuthor() { return author; }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            //TODO:Options
            return true;
        }
    }

    public static class ImagePostViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private WeakReference<ClickListener> listenerRef;
        private final TextView title;
        private final TextView description;
        private final TextView author;
        private final ImageView image;

        public ImagePostViewHolder(View view, ClickListener listener) {
            super(view);
            listenerRef = new WeakReference<>(listener);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.image);
            view.setOnClickListener(this);
        }
        public TextView getDescription() { return description; }
        public TextView getTitle() {
            return title;
        }
        public ImageView getImage() { return image; }
        public TextView getAuthor() { return author; }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            //TODO:Options
            return true;
        }
    }

    public static class PetitionPostViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private WeakReference<ClickListener> listenerRef;
        private final TextView title;
        private final TextView description;
        private final TextView author;
        private final ImageView image;
        Button button;
        PostDTO postDTO;
        PostController postController;

        public PetitionPostViewHolder(View view, ClickListener listener) {
            super(view);
            listenerRef = new WeakReference<>(listener);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.image);
            button = view.findViewById(R.id.signature);
            button.setOnClickListener(this);
            view.setOnClickListener(this);
        }
        public TextView getDescription() { return description; }
        public TextView getTitle() {
            return title;
        }
        public ImageView getImage() { return image; }
        public TextView getAuthor() { return author; }
        public Button getButton() { return button; }

        public void setPostDTO(PostDTO postDTO) {
            this.postDTO = postDTO;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == button.getId()) {
                postController = RetrofitUtils
                        .getInstance()
                        .getRetrofit()
                        .create(PostController.class);
                postController
                        .voteForPost(postDTO.getId().intValue(), null)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                button.setText("signed");
                                button.setBackgroundColor(Color.parseColor("#329D9C"));
                                button.setTextColor(Color.WHITE);
                                button.setClickable(false);
                                Log.i("Sign", response.raw().request().headers().toString());
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("Sign Error", t.getMessage(), t);
                            }
                        });
            } else {
                listenerRef.get().onPositionClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            //TODO:Options
            return true;
        }
    }

    public static class VotingPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private WeakReference<ClickListener> listenerRef;
        private final TextView title;
        private final TextView author;
        RecyclerView recyclerView;
        LinearLayoutManager mLayoutManager;

        public VotingPostViewHolder(View view, ClickListener listener) {
            super(view);
            listenerRef = new WeakReference<>(listener);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            recyclerView = view.findViewById(R.id.options_recycle_view);
            mLayoutManager = new LinearLayoutManager(view.getContext());
            view.setOnClickListener(this);
        }
        public TextView getTitle() { return title; }
        public TextView getAuthor() { return author; }
        public RecyclerView getRecyclerView() { return recyclerView; }
        public LinearLayoutManager getLayoutManager() { return mLayoutManager; }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            //TODO:Options
            return true;
        }
    }

    public static class PlayOffPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private WeakReference<ClickListener> listenerRef;
        private final TextView title;
        private final TextView author;

        public PlayOffPostViewHolder(View view, ClickListener listener) {
            super(view);
            listenerRef = new WeakReference<>(listener);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            view.setOnClickListener(this);
        }
        public TextView getTitle() { return title; }
        public TextView getAuthor() { return author; }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            //TODO:Options
            return true;
        }
    }
}