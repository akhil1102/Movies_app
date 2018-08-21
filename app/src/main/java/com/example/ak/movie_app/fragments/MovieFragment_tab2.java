package com.example.ak.movie_app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.ak.movie_app.models.Movie;
import com.example.ak.movie_app.adapters.MyMovieRecyclerViewAdapter;
import com.example.ak.movie_app.R;

import java.util.List;

public class MovieFragment_tab2 extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 3;
    private OnListFragmentInteractionListener mListener;

    private static List<Movie> movies_list;


    public static List<Movie> getMovies_list() {
        return movies_list;
    }

    public static void setMovies_list(List<Movie> movies_list) {
        MovieFragment_tab2.movies_list = movies_list;
    }

    public MovieFragment_tab2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list2, container, false);
        RecyclerView myRecyclerView = view.findViewById(R.id.rv2);
        MyMovieRecyclerViewAdapter adapter2 = new MyMovieRecyclerViewAdapter(MovieFragment_tab2.getMovies_list(), getContext());
        myRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), mColumnCount));
        myRecyclerView.setAdapter(adapter2);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}
