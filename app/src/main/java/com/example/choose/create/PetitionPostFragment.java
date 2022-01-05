package com.example.choose.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.choose.R;

public class PetitionPostFragment extends Fragment {

    public PetitionPostFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate =  inflater.inflate(R.layout.fragment_petition_post, container, false);
        Button btn = inflate.findViewById(R.id.closeBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseType.close();
            }
        });
        return inflate;
    }
}