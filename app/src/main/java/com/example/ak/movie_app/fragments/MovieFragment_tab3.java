package com.example.ak.movie_app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.ak.movie_app.BuildConfig;
import com.example.ak.movie_app.interfaces.MovieApiService;
import com.example.ak.movie_app.models.Movie;
import com.example.ak.movie_app.adapters.MyMovieRecyclerViewAdapter;
import com.example.ak.movie_app.R;
import com.example.ak.movie_app.models.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ak.movie_app.activities.MainActivity.BASE_URL;

public class MovieFragment_tab3 extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 3;
    private OnListFragmentInteractionListener mListener;
    private static List<Movie> movies_list = new ArrayList<>();
    MyMovieRecyclerViewAdapter adapter3;

    public static List<Movie> getMovies_list() {
        return movies_list;
    }

    public static void setMovies_list(List<Movie> movies_list) {
        MovieFragment_tab3.movies_list = movies_list;
    }

    public MovieFragment_tab3() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("in", "onCreate frag3");
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        fetchData();
    }

    private void fetchData() {
        Retrofit retrofit = null;
        String apikey = BuildConfig.Apikey;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> callTopRatedList = movieApiService.getTopRatedMovies(apikey);
        callTopRatedList.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> topRatedList = response.body().getResults();
                for (Movie movie : topRatedList) {
                    movies_list.add(movie);
                }
                adapter3.notifyDataSetChanged();
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
        View view = inflater.inflate(R.layout.fragment_movie_list3, container, false);
        RecyclerView myRecyclerView = view.findViewById(R.id.rv3);
        Log.d("in", "onCreate frag3");
//        Log.d("tab3","listSize"+movies_list.size());
        adapter3 = new MyMovieRecyclerViewAdapter(movies_list, getContext());
        myRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), mColumnCount));
        myRecyclerView.setAdapter(adapter3);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        Log.d("in", "onAttach frag3");
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
        Log.d("in", "onDetach frag3");
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}
