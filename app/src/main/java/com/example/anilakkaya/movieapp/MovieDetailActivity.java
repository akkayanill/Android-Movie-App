package com.example.anilakkaya.movieapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MovieDetailActivity extends AppCompatActivity {

    Gson gson;
    private MySharedPreference sharedPreference;
    private ArrayList<Movie> favoriteMovies;

    private Movie movie;
    private String KEY = "KEY";

    private String TAG = "";
    private ImageView imageView;
    private TextView titleTextView;
    private TextView ratingTextView;
    private TextView durationTextView;
    private TextView genreTextView;
    private TextView plotTextView;
    private TextView castTextView;
    private Button goBackButton;
    private LikeButton addToFavoritesButton;


    /***
     * Gets passed movie from another activity
     */
    private void getCurrentMovie() {
        //Get Movie from other Activity
        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra(KEY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        sharedPreference = new MySharedPreference(getApplicationContext());
        gson = new Gson();
        setupObjects();
        getCurrentMovie();
        loadFavoriteMovies();

        titleTextView.setText(movie.getTitle());
        ratingTextView.setText(String.valueOf(movie.getRating()));
        durationTextView.setText(String.valueOf(movie.getRuntime()));
        genreTextView.setText(movie.getGenre());
        plotTextView.setText(movie.getPlot());
        new DownloadImageTask(imageView).execute(movie.getImage());
        castTextView.setText(movie.getActors());


        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //to go back to main menu
            }
        });

        Log.e(TAG, "onCreate: is fav / : "+ movie.isFavorite());
        addToFavoritesButton.setLiked(movie.isFavorite());
        addToFavoritesButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                movie.setFavorite(true);
                favoriteMovies.add(movie);
                addMovieToFavorites(favoriteMovies);
                Toast.makeText(MovieDetailActivity.this, "Added to the list", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                Iterator itr = favoriteMovies.iterator();
                while (itr.hasNext()) {
                    Movie x = (Movie)itr.next();

                    //if (containsName(favoriteMovies, movie.getTitle())) {
                    if (x.getTitle().equals(movie.getTitle())) {
                        Log.e(TAG, "\n Remove This : "+ x);
                        itr.remove();
                    }
                }
                movie.setFavorite(false);
                addMovieToFavorites(favoriteMovies);
                Toast.makeText(MovieDetailActivity.this, "Removed from the list ", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "\n\n\n unLiked: REMOVE OGLUM "+ favoriteMovies );
            }
        });
    }


    private void addMovieToFavorites(ArrayList<Movie> movieList) {
        String json = gson.toJson(movieList);
        sharedPreference.addNewMovie(json);
    }

    /***
     * Loads favorite Movies
     * Checks if the movie is favorite movie
     */
    private void loadFavoriteMovies(){
        String jsonScore = sharedPreference.getMovieList();
        Type type = new TypeToken<List<Movie>>(){}.getType();
        favoriteMovies = gson.fromJson(jsonScore, type);

        Log.e(TAG, "loadFavoriteMovies: " + favoriteMovies);
        if (favoriteMovies == null) {
            favoriteMovies = new ArrayList<>();
        }

        //Checks if the movie is favorite movie
        if (containsName(favoriteMovies, movie.getTitle())) {
            movie.setFavorite(true);
        }
    }

    /***
     * to check if List has the given name
     *
     * @param list
     * @param name
     * @return
     */
    public boolean containsName(final List<Movie> list, final String name){
        return list.stream().map(Movie::getTitle).filter(name::equals).findFirst().isPresent();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    private void setupObjects() {
        addToFavoritesButton = findViewById(R.id.addToFavorites);
        goBackButton = findViewById(R.id.goBackButton);
        imageView = findViewById(R.id.imageView);
        titleTextView = findViewById(R.id.titleTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        durationTextView  = findViewById(R.id.durationTextView);
        genreTextView  = findViewById(R.id.genreTextView);
        plotTextView = findViewById(R.id.plotTextView);
        castTextView = findViewById(R.id.castTextView);
    }

}
