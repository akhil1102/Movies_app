package com.example.ak.movie_app.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.ak.movie_app.R;
import com.example.ak.movie_app.adapters.ViewPagerAdapter;
import com.example.ak.movie_app.fragments.MovieFragment_tab1;
import com.example.ak.movie_app.fragments.MovieFragment_tab2;
import com.example.ak.movie_app.fragments.MovieFragment_tab3;

public class MainActivity extends AppCompatActivity implements
        MovieFragment_tab1.OnListFragmentInteractionListener,
        MovieFragment_tab2.OnListFragmentInteractionListener,
        MovieFragment_tab3.OnListFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}