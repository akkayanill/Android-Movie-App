package com.example.anilakkaya.movieapp;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String URL_DATA = "http://www.omdbapi.com/?t=batman&apikey=3976b824";
    private String KEY = "KEY";

    Movie[] MOVIES = {
            new Movie("BATMAN","15.2.2010","160 min", "Thriller", "Christopher Nolan", "null for now", 8.7) ,
            new Movie("SUPERMAN","15.2.2010","160 min", "Thriller", "Christopher Nolan", "null for now", 8.7),
            new Movie("BATMAN","15.2.2010","160 min", "Thriller", "Christopher Nolan", "null for now", 8.7),
            new Movie("INCEPTION","15.2.2010","160 min", "Thriller", "Christopher Nolan", "null for now", 8.7),
            new Movie("KIBAR FEYZO","15.2.2010","160 min", "Comedy", "Christopher Nolan", "null for now", 8.7)
    };

    List<Movie> movieList = new ArrayList<Movie>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        final CustomAdapter customAdapter = new CustomAdapter(this,MOVIES);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie item = (Movie) customAdapter.getItem(position);

                Intent newIntent = new Intent(view.getContext(),MovieDetailActivity.class);
                newIntent.putExtra(KEY,item);
                startActivity(newIntent);
            }
        });
    }


    private void fetchJson() {
        Gson gson = new Gson();
        String json = "{\"Title\":\"John Wick\",\"Released\":30,\"Runtime\":\"20\"}";
        Movie movie = gson.fromJson(json, Movie.class);
        System.out.println("lan amk : "+movie.toString());
    }


}
