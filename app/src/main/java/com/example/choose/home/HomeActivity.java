package com.example.choose.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.choose.ChooseType2;
import com.example.choose.create.ChooseType;
import com.example.choose.R;
import com.example.choose.home.user.PersonalPageFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomAppBar bar = findViewById(R.id.bottomAppBar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.profile);

        MenuItem item1 = bottomNavigationView.getMenu().findItem(R.id.home);
        MenuItem item2 = bottomNavigationView.getMenu().findItem(R.id.communities);
        MenuItem item3 = bottomNavigationView.getMenu().findItem(R.id.notifications);
        MenuItem item4 = bottomNavigationView.getMenu().findItem(R.id.profile);

        MenuItem empty = bottomNavigationView.getMenu().findItem(R.id.itemEmpty);
        empty.setEnabled(false);

        FloatingActionButton btn2 = findViewById(R.id.fab_start);
        btn2.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ChooseType2.class)));

        item4.setEnabled(true);

        item4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                displayView(0);
                return false;
            }
        });

        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                displayView(1);
                return false;
            }
        });

        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                displayView(2);
                return false;
            }
        });

        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                displayView(3);
                return false;
            }
        });

        displayView(0);
    }

    public void showFragment(Fragment fragment, int position) {
        FragmentTransaction mTransactiont = getSupportFragmentManager().beginTransaction();
        mTransactiont.replace(R.id.main_container, fragment, fragment.getClass().getName());
        mTransactiont.commit();
    }

    public void displayView(int position) {
        switch (position) {
            case 0:
                showFragment(new PersonalPageFragment(), position);
                break;
            case 1:
                showFragment(new NotificationsFragment(), position);
                break;
            case 2:
                showFragment(new CommunitiesFragment(), position);
                break;
            case 3:
                showFragment(new FeedFragment(), position);
                break;
        }
    }
}