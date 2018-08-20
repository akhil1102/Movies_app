package com.example.ak.movie_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ak.movie_app.MovieFragment_tab2.OnListFragmentInteractionListener;
import com.example.ak.movie_app.dummy.DummyContent.DummyItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {

//    private final List<DummyItem> mValues;
//    private final OnListFragmentInteractionListener mListener;

    List<Movie> movies_list;
    Context mContext;

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
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);

        holder.title.setText(movies_list.get(position).getTitle());
        Picasso.get().load(movies_list.get(position).getImage_url()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("clicked","clicked");
                Intent intent = new Intent(mContext, Movie_detail.class);
                intent.putExtra("image_url",movies_list.get(position).getImage_url());
                intent.putExtra("title",movies_list.get(position).getTitle());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movies_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
//        public DummyItem mItem;

        private TextView title;
        private ImageView image;

        public ViewHolder(View view) {
            super(view);
//            mView = view;
            title = view.findViewById(R.id.tv_title);
            image = view.findViewById(R.id.poster);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
