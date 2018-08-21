package com.example.ak.movie_app.activities;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ak.movie_app.BuildConfig;
import com.example.ak.movie_app.dataHandlers.Movie;
import com.example.ak.movie_app.R;
import com.example.ak.movie_app.adapters.ViewPagerAdapter;
import com.example.ak.movie_app.fragments.MovieFragment_tab1;
import com.example.ak.movie_app.fragments.MovieFragment_tab2;
import com.example.ak.movie_app.fragments.MovieFragment_tab3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity
        implements MovieFragment_tab1.OnListFragmentInteractionListener,
        MovieFragment_tab2.OnListFragmentInteractionListener,
        MovieFragment_tab3.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String apikey = BuildConfig.Apikey;
        String now_playing_url = "https://api.themoviedb.org/3/movie/now_playing";
        String popular_url = "https://api.themoviedb.org/3/movie/popular";
        String top_rated_url = "https://api.themoviedb.org/3/movie/top_rated";
        String[] urls = new String[]{now_playing_url, popular_url, top_rated_url};

        String[] final_urls = new String[3];
        for(int i=0; i<urls.length; i++) {
            final_urls[i] = Uri.parse(urls[i])
                    .buildUpon().appendQueryParameter("api_key", apikey)
                    .appendQueryParameter("language", "en-US")
                    .appendQueryParameter("page", "1").build().toString();

        }
        new GetData().execute(final_urls);
    }

    private class GetData extends AsyncTask<String[], Void, ArrayList<ArrayList<Movie>>> {

        //image path constants, can be used to change image size
        final String base_url_image = "https://image.tmdb.org/t/p/";
        final String image_size = "w185";


        @TargetApi(Build.VERSION_CODES.KITKAT)
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected ArrayList<ArrayList<Movie>> doInBackground(String[]... params){

            URLConnection uc;
            HttpsURLConnection httpconnection = null;

            String[] url_array = params[0];
            ArrayList<ArrayList<Movie>> all_movies = new ArrayList<>();
            for (String anUrl_array : url_array) {
                ArrayList<Movie> movies_list = new ArrayList<>();
                try {
                    URL u = new URL(anUrl_array);
                    uc = u.openConnection();
                    httpconnection = (HttpsURLConnection) uc;
                    int responseCode = httpconnection.getResponseCode();
                    Log.d("Response code", ":" + responseCode);
                    InputStream inputStream = httpconnection.getInputStream();
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    StringBuilder sBuilder = new StringBuilder();

                    String line;
                    while ((line = bReader.readLine()) != null) {
                        sBuilder.append(line).append("\n");
                    }

                    Log.d("url", anUrl_array);
                    inputStream.close();
                    String result = sBuilder.toString();
                    JSONObject json = new JSONObject(result);
                    JSONArray jsonArray = json.getJSONArray("results");


                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject obj = jsonArray.getJSONObject(j);
                        String title = obj.getString("original_title");
                        Log.d("Title:", title);
                        String imageUrlFromJson = obj.getString("poster_path");
                        StringBuilder sb = new StringBuilder();
                        sb.append(base_url_image);
                        sb.append(image_size);
                        sb.append(imageUrlFromJson);
                        String image_finalPath = sb.toString();
                        Log.d("poster:", image_finalPath);
                        Movie m = new Movie(title, image_finalPath);
                        movies_list.add(m);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpconnection != null) {
                        httpconnection.disconnect();
                    }

                }

                all_movies.add(movies_list);
            }
            return all_movies;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<Movie>> movies) {
            MovieFragment_tab1.setMovies_list(movies.get(0));
            MovieFragment_tab2.setMovies_list(movies.get(1));
            MovieFragment_tab3.setMovies_list(movies.get(2));
            TabLayout tabLayout = findViewById(R.id.tab_layout);
            ViewPager viewPager = findViewById(R.id.view_pager);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setupWithViewPager(viewPager);

        }
    }
}
