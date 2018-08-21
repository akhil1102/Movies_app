package com.example.ak.movie_app;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;

import com.example.ak.movie_app.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements MovieFragment_tab1.OnListFragmentInteractionListener, MovieFragment_tab2.OnListFragmentInteractionListener, MovieFragment_tab3.OnListFragmentInteractionListener{

    private ListView listView ;
    private MovieAdapter mAdapter;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
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

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);


        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {

//        switch(item.getItemId()){
//            case R.id.Grid:
//                //make grid
//                gridView = findViewById(R.id.grid_view);
//                gridView.setVisibility(View.VISIBLE);
//                listView.setVisibility(View.GONE);
//                return true;
//
//            case R.id.List:
//                //make list
//                listView.setVisibility(View.VISIBLE);
//                gridView.setVisibility(View.GONE);
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private class GetData extends AsyncTask<String[], Void, ArrayList<ArrayList<Movie>>> {

        //image path constants, can be used to change image size
        final String base_url_image = "https://image.tmdb.org/t/p/";
        final String image_size = "w185";


        @TargetApi(Build.VERSION_CODES.KITKAT)
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected ArrayList<ArrayList<Movie>> doInBackground(String[]... params){

            URLConnection uc = null;
            HttpsURLConnection httpconnection = null;

            String[] url_array = params[0];
            ArrayList<ArrayList<Movie>> all_movies = new ArrayList<>();
            for(int i=0; i<url_array.length; i++) {
                ArrayList<Movie> movies_list = new ArrayList<Movie>();
                String url = url_array[i];
                try {
                    URL u = new URL(url);
                    uc = u.openConnection();
                    httpconnection = (HttpsURLConnection) uc;
                    int responseCode = httpconnection.getResponseCode();
                    Log.d("Response code", ":" + responseCode);
                    InputStream inputStream = httpconnection.getInputStream();
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    StringBuilder sBuilder = new StringBuilder();

                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sBuilder.append(line + "\n");
                    }

                    Log.d("url", url);
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
                    httpconnection.disconnect();

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
