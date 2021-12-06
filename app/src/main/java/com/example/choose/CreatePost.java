package com.example.choose;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toolbar;

import com.example.choose.api.PostController;
import com.example.choose.dto.GetFeedRequestDTO;
import com.example.choose.dto.GetFeedResponseDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.recycler.post.PostAdapter;

import com.example.choose.ui.PostFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreatePost extends AppCompatActivity {

    private PostAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);


        FloatingActionButton btn2 = findViewById(R.id.fab_start);
        btn2.setOnClickListener(v -> startActivity(new Intent(CreatePost.this, ChooseType.class)));

        BottomAppBar bar = findViewById(R.id.bottomAppBar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setSelectedItemId(R.id.profile);
        MenuItem item = bottomNavigationView.getMenu().findItem(R.id.profile);

        item.setIcon(R.drawable.ic_baseline_person_24);
        item.setEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);


//        RecyclerView contentView = findViewById(R.id.content_recycle_view);
//        contentView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new PostAdapter();
//        contentView.setAdapter(adapter);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request builder = chain
//                        .request()
//                        .newBuilder()
//                        .addHeader("Authorization", "basic dGVzdDp0ZXN0")
//                        .build();
//                return chain.proceed(builder);
//            }
//        }).build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://choose.teheidoma.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        PostController postController = retrofit.create(PostController.class);
//
//        AsyncTask.execute(() -> {
//            postController
//                    .getFeed(new GetFeedRequestDTO(0,0,10))
//                    .enqueue(new Callback<GetFeedResponseDTO>() {
//                        @Override
//                        public void onResponse(Call<GetFeedResponseDTO> call, Response<GetFeedResponseDTO> response) {
//                            if (response.isSuccessful()) {
//                                adapter.localDataSet.addAll(response.body().getPosts());
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<GetFeedResponseDTO> call, Throwable t) {
//                            Log.e("post", t.getMessage(), t);
//                        }
//                    });
//        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PostFragment(), "Posts");
        adapter.addFragment(new CommentsFragment(), "Comments");
        adapter.addFragment(new DiscoverFragment(), "Discover");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}