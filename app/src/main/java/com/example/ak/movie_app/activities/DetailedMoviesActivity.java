package com.example.ak.movie_app.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ak.movie_app.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailedMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("title")){
            String image_url = getIntent().getStringExtra("image_url");
            String title = getIntent().getStringExtra("title");
            String plotOverview = getIntent().getStringExtra("overview");
            double voteAverage = getIntent().getDoubleExtra("voteAverage",0);
            String releaseDate = getIntent().getStringExtra("releaseDate");
            String backdrop = getIntent().getStringExtra("backdrop");
            setViews(image_url, title, plotOverview, voteAverage, releaseDate, backdrop);
        }
    }

    private void setViews(String image_url, String title, String plotOverview, double voteAverage, String releaseDate, String backdrop){

        String base_url_image = "https://image.tmdb.org/t/p/";
        String poster_size = "w300";
        String backdrop_size = "w780";

        StringBuilder sb_poster = new StringBuilder();
        sb_poster.append(base_url_image);
        sb_poster.append(poster_size);
        sb_poster.append(image_url);
        ImageView image_poster = findViewById(R.id.poster);
        Picasso.get().load(sb_poster.toString()).into(image_poster);

        TextView title_detail = findViewById(R.id.detail_title);
        title_detail.setText(title);

        StringBuilder sb_backdrop = new StringBuilder();
        sb_backdrop.append(base_url_image).append(backdrop_size);
        ImageView imageView_detail = findViewById(R.id.collapse_poster_detail);
        Picasso.get().load(sb_backdrop.append(backdrop).toString()).into(imageView_detail);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(title);

        TextView overview = findViewById(R.id.plotOverview);
        overview.setText(plotOverview);

        TextView vote = findViewById(R.id.voteAverage);
        vote.setText("TMDb rating: "+voteAverage);

        TextView release_date = findViewById(R.id.releaseDate);
        release_date.setText("Release Date: "+releaseDate);

    }
}
