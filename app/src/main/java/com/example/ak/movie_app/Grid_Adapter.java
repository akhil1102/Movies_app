package com.example.ak.movie_app;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class Grid_Adapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Movie> movie_list_grid = new ArrayList<>();

    public Grid_Adapter(Context mContext, ArrayList<Movie> movie_list) {
        this.mContext = mContext;
        this.movie_list_grid = movie_list;
    }

    @Override
    public int getCount() {
        return movie_list_grid.size();
    }

    public Object getItem(int position){
        return null;
    }
    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        }
        Movie currentMovie = movie_list_grid.get(position);
        ImageView image = listItem.findViewById(R.id.poster);
        if(currentMovie != null){
            Picasso.get().load(currentMovie.getImage_url()).into(image);
        }

        TextView title_grid  = listItem.findViewById(R.id.title);
        title_grid.setText(currentMovie.getTitle());
        return listItem;

    }
}
