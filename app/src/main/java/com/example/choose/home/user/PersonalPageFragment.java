package com.example.choose.home.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.choose.R;
import com.example.choose.retrofit.RetrofitUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PersonalPageFragment extends Fragment {

    public PersonalPageFragment() { }

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView welcome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        welcome = view.findViewById(R.id.welcome);
        String name = RetrofitUtils.getInstance().getUsername();
        if(name.contains("@"))
            for (int i = 0; i < name.length(); i++)
                if (name.charAt(i) == '@') name = name.substring(0,i);
        welcome.setText("Welcome on Board " + name + "!");
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
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