package id.web.wfebriadi.submission5.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.web.wfebriadi.submission5.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = (ViewPager)v.findViewById(R.id.view_pager);
        viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));

        tabLayout = (TabLayout)v.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return v;
    }

    private class sliderAdapter extends FragmentPagerAdapter {

        String sedang_tayang = getResources().getString(R.string.now_playing);
        String akan_datang = getResources().getString(R.string.upcomming);
        final String tabs[] = {sedang_tayang, akan_datang};

        public sliderAdapter(FragmentManager fm){
            super(fm);
        }
        public Fragment getItem(int position){
            switch (position){
                case 0:
                    return new NowPlayingFragment();
                case 1:
                    return new UpcommingFragment();
            }
            return null;
        }
        public int getCount(){
            return tabs.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

}
