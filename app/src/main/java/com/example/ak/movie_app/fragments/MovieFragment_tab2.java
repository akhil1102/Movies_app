package com.example.ak.movie_app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ak.movie_app.BuildConfig;
import com.example.ak.movie_app.R;
import com.example.ak.movie_app.adapters.MyMovieRecyclerViewAdapter;
import com.example.ak.movie_app.models.Movie;
import com.example.ak.movie_app.models.MovieResponse;
import com.example.ak.movie_app.networkHandlers.APIClient;
import com.example.ak.movie_app.networkHandlers.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment_tab2 extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private MyMovieRecyclerViewAdapter adapter;

    private static List<Movie> movies_list = new ArrayList<>();

    public MovieFragment_tab2() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            movies_list = savedInstanceState.getParcelableArrayList("movies_list");
        }
        else {
            fetchData();
        }
    }

    private void fetchData() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MovieResponse> callPopular = apiInterface.getPopularMovies(BuildConfig.APIKEY);
        callPopular.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> popularList;
                try {
                    if (response.body() != null && response.body().getResults() != null) {
                        popularList = response.body().getResults();
                        movies_list.addAll(popularList);
                    }
                    adapter.notifyDataSetChanged();
                } catch (NullPointerException ex) {
                    Log.d("Exception",ex.getMessage());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list2, container, false);
        RecyclerView myRecyclerView = view.findViewById(R.id.rv2);
        adapter = new MyMovieRecyclerViewAdapter(movies_list, getContext());
        int mColumnCount = 3;
        myRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), mColumnCount));
        myRecyclerView.setAdapter(adapter);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies_list", (ArrayList<? extends Parcelable>) movies_list);
    }

    public interface OnListFragmentInteractionListener { }
}
