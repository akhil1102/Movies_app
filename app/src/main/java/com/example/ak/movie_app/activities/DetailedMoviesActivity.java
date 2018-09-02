package com.example.ak.movie_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ak.movie_app.R;
import com.squareup.picasso.Picasso;

public class DetailedMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("title")){
            String image_url = getIntent().getStringExtra("image_url");
            String title = getIntent().getStringExtra("title");
            setViews(image_url, title);
        }
    }

    private void setViews(String image_url, String title){
        TextView title_detail = findViewById(R.id.title_detail);
        title_detail.setText(title);
        ImageView imageView_detail = findViewById(R.id.poster_detail);
        Picasso.get().load(image_url).into(imageView_detail);
    }
}
