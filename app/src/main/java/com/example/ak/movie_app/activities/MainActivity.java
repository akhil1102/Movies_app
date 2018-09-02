package com.example.ak.movie_app.activities;

import android.annotation.TargetApi;

import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.ak.movie_app.BuildConfig;
import com.example.ak.movie_app.R;
import com.example.ak.movie_app.adapters.ViewPagerAdapter;
import com.example.ak.movie_app.fragments.MovieFragment_tab1;
import com.example.ak.movie_app.fragments.MovieFragment_tab2;
import com.example.ak.movie_app.fragments.MovieFragment_tab3;
import com.example.ak.movie_app.interfaces.MovieApiService;
import com.example.ak.movie_app.models.Movie;
import com.example.ak.movie_app.models.MovieResponse;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieFragment_tab1.OnListFragmentInteractionListener, MovieFragment_tab2.OnListFragmentInteractionListener, MovieFragment_tab3.OnListFragmentInteractionListener {


    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    //    @Override
//    public void onListFragmentInteraction(DummyContent.DummyItem item) {
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}