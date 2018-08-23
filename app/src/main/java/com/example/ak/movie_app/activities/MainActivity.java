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

import com.example.ak.movie_app.BuildConfig;
import com.example.ak.movie_app.R;
import com.example.ak.movie_app.adapters.ViewPagerAdapter;
import com.example.ak.movie_app.fragments.MovieFragment_tab1;
import com.example.ak.movie_app.fragments.MovieFragment_tab2;
import com.example.ak.movie_app.fragments.MovieFragment_tab3;
import com.example.ak.movie_app.interfaces.MovieApiService;
import com.example.ak.movie_app.models.Movie;
import com.example.ak.movie_app.models.MovieResponse;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieFragment_tab1.OnListFragmentInteractionListener, MovieFragment_tab2.OnListFragmentInteractionListener, MovieFragment_tab3.OnListFragmentInteractionListener{


    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    String apikey = BuildConfig.Apikey;

//    @Override
//    public void onListFragmentInteraction(DummyContent.DummyItem item) {
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        GetApiData();
//        String now_playing_url = "https://api.themoviedb.org/3/movie/now_playing";
//        String popular_url = "https://api.themoviedb.org/3/movie/popular";
//        String top_rated_url = "https://api.themoviedb.org/3/movie/top_rated";
//        String[] urls = new String[]{now_playing_url, popular_url, top_rated_url};
//
//        String[] final_urls = new String[3];
//        for(int i=0; i<urls.length; i++) {
//            final_urls[i] = Uri.parse(urls[i])
//                    .buildUpon().appendQueryParameter("api_key", apikey)
//                    .appendQueryParameter("language", "en-US")
//                    .appendQueryParameter("page", "1").build().toString();
//
//        }
//        new GetData().execute(final_urls);


    }

    private void GetApiData() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> callNowPlaying = movieApiService.getNowPlayingMovies(apikey);
        callNowPlaying.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> nowPlayingList = response.body().getResults();
                MovieFragment_tab1.setMovies_list(nowPlayingList);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Call<MovieResponse> callPopular = movieApiService.getPopularMovies(apikey);
        callPopular.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> popularList = response.body().getResults();
                MovieFragment_tab2.setMovies_list(popularList);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Call<MovieResponse> callTopRatedList = movieApiService.getTopRatedMovies(apikey);
        callTopRatedList.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> topRatedList = response.body().getResults();
                MovieFragment_tab3.setMovies_list(topRatedList);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
    }



        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);


        return true;
    }

}
