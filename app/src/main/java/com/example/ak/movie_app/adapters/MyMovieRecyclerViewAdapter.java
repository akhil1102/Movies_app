package com.example.ak.movie_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ak.movie_app.R;
import com.example.ak.movie_app.activities.DetailedMoviesActivity;
import com.example.ak.movie_app.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {


    private List<Movie> movies_list;
    private Context mContext;

    public MyMovieRecyclerViewAdapter(List<Movie> movies, Context mContext) {
        movies_list = movies;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_carditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(movies_list.get(position).getTitle());
        String base_url_image = "https://image.tmdb.org/t/p/";
        String poster_size = "w185";
        StringBuilder sb = new StringBuilder();
        sb.append(base_url_image);
        sb.append(poster_size);
        sb.append(movies_list.get(position).getPoster_path());
        Picasso.get().load(sb.toString()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DetailedMoviesActivity.class);
                intent.putExtra("image_url", movies_list.get(position).getPoster_path());
                intent.putExtra("title", movies_list.get(position).getTitle());
                intent.putExtra("overview", movies_list.get(position).getOverview());
                intent.putExtra("voteAverage", movies_list.get(position).getVote_average());
                intent.putExtra("releaseDate",movies_list.get(position).getRelease_date());
                intent.putExtra("backdrop",movies_list.get(position).getBackdrop_path());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (movies_list != null) {
            return movies_list.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_title);
            image = view.findViewById(R.id.poster);
        }
    }
}
