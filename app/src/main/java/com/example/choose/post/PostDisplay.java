package com.example.choose.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.choose.R;
import com.example.choose.create.PlayOffChooseFragment;
import com.example.choose.home.CommunitiesFragment;
import com.example.choose.home.FeedFragment;
import com.example.choose.home.HomeActivity;
import com.example.choose.home.NotificationsFragment;
import com.example.choose.home.user.PersonalPageFragment;

public class PostDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_display);

        TextView title = findViewById(R.id.textView10);
        TextView name = findViewById(R.id.name);
        Button button = findViewById(R.id.sendBtn);
        int id;
        String type;

        Bundle extras = getIntent().getExtras();

        title.setText(extras.getString("title"));
        type = extras.getString("type");
        id = extras.getInt("id");


        switch (type){
            case "IMAGE":
                displaySecondView(extras.getString("desc"), extras.getString("url"));
                name.setText("Image Post");
                break;
            case "PETITION":
                //TODO
                break;
            case "VOTE":
                //TODO
                break;
            case "PLAYOFF":
                //TODO
                break;
            default:
                displayFirstView(extras.getString("cont"));
                break;
        }


        button.setOnClickListener(v -> startActivity(new Intent(PostDisplay.this, HomeActivity.class)));
    }

    public void showFragment(Fragment fragment, int position) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.display_container, fragment, fragment.getClass().getName());
        mTransaction.commit();
    }

    public void displayFirstView(String desc) {
        showFragment(new TextPostDisplayFragment(desc),0);
    }

    public void displaySecondView(String desc, String url) {
        showFragment(new ImagePostDisplayFragment(desc, url),1);
    }
}