package com.example.ak.movie_app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.ak.movie_app.dataHandlers.Movie;
import com.example.ak.movie_app.adapters.MyMovieRecyclerViewAdapter;
import com.example.ak.movie_app.R;

import java.util.ArrayList;

public class MovieFragment_tab1 extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 3;
    private static ArrayList<Movie> movies_list = new ArrayList<>();

    public static ArrayList<Movie> getMovies_list() {
        return movies_list;
    }

    public static void setMovies_list(ArrayList<Movie> movies_list) {
        MovieFragment_tab1.movies_list = movies_list;
    }

    public MovieFragment_tab1() {

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

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        RecyclerView myRecyclerView2 = view.findViewById(R.id.rv1);
        MyMovieRecyclerViewAdapter adapter1 = new MyMovieRecyclerViewAdapter(MovieFragment_tab1.getMovies_list(), getContext());
        myRecyclerView2.setLayoutManager(new GridLayoutManager(this.getContext(), mColumnCount));
        myRecyclerView2.setAdapter(adapter1);
        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}
