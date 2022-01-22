package com.example.choose.post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.choose.R;
import com.example.choose.api.PostController;
import com.example.choose.dto.PetitionPostDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.retrofit.RetrofitUtils;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayOffPostDisplayFragment extends Fragment {
    Integer id;

    public PlayOffPostDisplayFragment() { }

    public PlayOffPostDisplayFragment(Integer id) {
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_play_off_post_display, container, false);
        return inflate;
    }
}