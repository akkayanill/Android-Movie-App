package com.example.anilakkaya.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteMoviesActivity extends AppCompatActivity {

    //private final String URL_DATA = "http://www.omdbapi.com/?t=batman&apikey=3976b824";
    private String TAG = "";
    private String KEY = "KEY";
    Gson gson;
    private MySharedPreference sharedPreference;
    private ArrayList<Movie> favoriteMovies;

    ListView listView;
    CustomAdapter customAdapter;

//    Movie[] MOVIES = {};
//    Movie[] MOVIES = {
//            new Movie("BATMAN","15.2.2010","160 min", "Thriller", "Christopher Nolan", "null for now", 8.7) ,
//            new Movie("SUPERMAN","15.2.2010","160 min", "Thriller", "Christopher Nolan", "null for now", 8.7),
//            new Movie("BATMAN","15.2.2010","160 min", "Thriller", "Christopher Nolan", "null for now", 8.7),
//            new Movie("INCEPTION","15.2.2010","160 min", "Thriller", "Christopher Nolan", "null for now", 8.7),
//            new Movie("KIBAR FEYZO","15.2.2010","160 min", "Comedy", "Christopher Nolan", "null for now", 8.7)
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreference = new MySharedPreference(getApplicationContext());
        gson = new Gson();
        loadFavoriteMovies();

        setupListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = (Movie) customAdapter.getItem(position);
                goToMovieDetail(movie);
            }
        });
    }

    private void setupListView() {
        listView = findViewById(R.id.listView);
        customAdapter = new CustomAdapter(this,favoriteMovies);
        listView.setAdapter(customAdapter);
    }

    /***
     * Removes a given movie from favorites list and from memory
     */
    private void removeMovieFromFavorites() { }
    /***
     * Loads favorite Movies
     */
    private void loadFavoriteMovies(){

        String jsonScore = sharedPreference.getMovieList();
        Type type = new TypeToken<List<Movie>>(){}.getType();
        favoriteMovies = gson.fromJson(jsonScore, type);

        if (favoriteMovies == null) {
            favoriteMovies = new ArrayList<>();
        }
        Log.d(TAG, "loadFavoriteMovies: " + favoriteMovies);
    }

    /***
     * Creates new intent and
     * @param movie
     */
    private void goToMovieDetail(Movie movie) {
        Intent newIntent = new Intent(this,MovieDetailActivity.class);
        newIntent.putExtra(KEY,movie);
        startActivity(newIntent);
    }



}
