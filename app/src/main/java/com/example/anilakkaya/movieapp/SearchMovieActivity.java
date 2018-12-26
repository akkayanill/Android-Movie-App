package com.example.anilakkaya.movieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.*;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
public class SearchMovieActivity extends AppCompatActivity {

    private EditText searchTextView;
    private Button searchButton;
    private ImageView searchImageView;
    private ProgressDialog pd;
    private Movie currentMovie;
//    private ArrayList<Movie> movies = new ArrayList<Movie>();
    RequestQueue queue;
    private String KEY = "KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        setupObjects();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = searchTextView.getText().toString();
                String url = "http://www.omdbapi.com/?t=";
                String apiKey = "&apikey=3976b824";

                if (text != ""){
                    Log.e("url " , url.concat(text).concat(apiKey));
                    String newUrl = url.concat(text).concat(apiKey);
                    getJson(newUrl);
                }

            }
        });

    }
    private void getJson(String url) {
        pd = new ProgressDialog(SearchMovieActivity.this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);
        pd.show();
        retrieveStream(url);
        Log.e("movie","basladi");

    }
    private void retrieveStream(String url){
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String title = obj.getString("Title");
                            String release = obj.getString("Released");
                            String runtime = obj.getString("Runtime");
                            String genre = obj.getString("Genre");
                            String director = obj.getString("Title");
                            String image = obj.getString("Poster");
                            Double rating = obj.getDouble("imdbRating");
                            Log.e("done","rating :" + rating);
                            String plot = obj.getString("Plot");
                            String actors = obj.getString("Actors");

                            currentMovie = new Movie(title, release, plot, runtime, genre, director, image,rating,actors);
                            pd.dismiss();
                            Log.e("done","DONE");
                            goToNextScreen(currentMovie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("asd","Didn't work!!!");
            }
        });

        queue.add(stringRequest);
    }

    private void goToNextScreen(Movie movie) {
        Intent intent = new Intent(this,MovieDetailActivity.class);
        intent.putExtra(KEY,movie);
        startActivity(intent);
    }

    private void setupObjects() {
        searchImageView = findViewById(R.id.search_imageView);
        searchImageView.setImageResource(R.drawable.popcorn);

        searchButton = findViewById(R.id.search_button);

        searchTextView = findViewById(R.id.search_textView);
        searchTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    searchTextView.setText("");
                }else {
                    searchTextView.setText("Search Movies...");
                }
            }
        });

    }

    public Movie getMovie() { return currentMovie; }

}
