package com.example.choose.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choose.R;
import com.example.choose.recycler.post.MyListItem;
import com.example.choose.recycler.post.MyRecyclerViewAdapter;

import java.util.ArrayList;

public class PlayOffSelectFragment extends Fragment {

    public PlayOffSelectFragment() { }

    ArrayList<MyListItem> myListItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate =  inflater.inflate(R.layout.fragment_play_off_select, container, false);

        RecyclerView items = (RecyclerView) inflate.findViewById(R.id.recycler);

        myListItems = MyListItem.createContactsList(getArguments().getInt("choice"));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(myListItems);
        items.setAdapter(adapter);
        items.setLayoutManager(new LinearLayoutManager(inflate.getContext()));

        return inflate;
    }
}
