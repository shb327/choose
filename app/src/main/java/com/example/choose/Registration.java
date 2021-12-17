package com.example.choose;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choose.api.LoginController;
import com.example.choose.ui.PostFragment;
import com.example.choose.ui.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(3);
        pageIndicatorView.setSelection(0);

        Button btn = (Button)findViewById(R.id.next_one_btn);
        TextView txt = (TextView)findViewById(R.id.login_btn);

        //TMP
        LoginController controller = RetrofitUtils.getInstance().getRetrofit().create(LoginController.class);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, LoginActivity.class));
            }
        });

        btn.setOnClickListener(v -> {
//            startActivity(new Intent(Registration.this, CreatePost.class));
            //TMP
            controller.login("test", "test")
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.i("post", response.raw().request().headers().toString());
                            if (response.code() == 200) {
                                String cookie = response.headers().get("Set-Cookie").split(";")[0].split("=")[1];
                                RetrofitUtils.getInstance().createRetrofit(cookie);
                                startActivity(new Intent(Registration.this, CreatePost.class));
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("Login Error", t.getMessage(), t);
                        }
                    });
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pageIndicatorView.setViewPager(viewPager);
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        Registration.ViewPagerAdapter adapter = new Registration.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstStepFragment(), "Uno");
        adapter.addFragment(new SecondStepFragment(), "Dos");
        adapter.addFragment(new ThirdStepFragment(), "Tres");
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