package com.example.choose.create;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.choose.R;
import com.example.choose.create.ChooseType;
import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.home.HomeActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayOffPostFragment extends Fragment {

    public PlayOffPostFragment() { }

    private int anInt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate =  inflater.inflate(R.layout.fragment_play_off_post, container, false);
        displayFirstView();

        anInt = 8;

        Button btn = inflate.findViewById(R.id.closeBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseType.close();
            }
        });

        View playOffContainer = inflate.findViewById(R.id.off_container);


        playOffContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayOffChooseFragment.isReady()){
                    setAnInt(PlayOffChooseFragment.getNumber());
                    displaySecondView();
                }
            }
        });
        return inflate;
    }

    public void showFragment(Fragment fragment, int position) {
        FragmentTransaction mTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.off_container, fragment, fragment.getClass().getName());
        mTransaction.commit();
    }

    public void displayFirstView() {
        showFragment(new PlayOffChooseFragment(),0);
    }

    public void displaySecondView() {
        PlayOffSelectFragment playOffSelectFragment = new PlayOffSelectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("choice", PlayOffChooseFragment.getNumber());
        playOffSelectFragment.setArguments(bundle);
        showFragment(playOffSelectFragment, 1);
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }
}