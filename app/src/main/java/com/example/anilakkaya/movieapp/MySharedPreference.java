package com.example.anilakkaya.movieapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    final int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "pref";
    private static final String MOVIES = "movies";

    public MySharedPreference(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public List<Movie> loadMovies() {
        List list = new ArrayList<Movie>();

        return list;
    }
    public void addNewMovie(String movieList) {
        editor.putString(MOVIES, movieList);
        editor.commit();
    }
    public void removeMovie(String movieList){

    }
    public String getMovieList() {return pref.getString(MOVIES, "");}

}
