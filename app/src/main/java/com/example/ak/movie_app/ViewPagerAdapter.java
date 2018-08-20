package com.example.ak.movie_app;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "Now Playing", "Popular", "Top Rated" };
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
     }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MovieFragment_tab1();

            case 1:
                return new MovieFragment_tab2();

            case 2:
                return new MovieFragment_tab3();


                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
