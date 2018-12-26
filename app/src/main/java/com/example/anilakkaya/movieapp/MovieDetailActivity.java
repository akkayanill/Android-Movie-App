package com.example.anilakkaya.movieapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleTextView;
    private TextView ratingTextView;
    private TextView durationTextView;
    private TextView genreTextView;
    private TextView plotTextView;
    private TextView castTextView;
    private Button goBackButton;

    private Movie movie;
    private String KEY = "KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        setupObjects();

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(KEY);

        titleTextView.setText(movie.getTitle());
        ratingTextView.setText(String.valueOf(movie.getRating()));
        durationTextView.setText(String.valueOf(movie.getRuntime()));
        genreTextView.setText(movie.getGenre());
        plotTextView.setText(movie.getPlot());
        new DownloadImageTask(imageView).execute(movie.getImage());
        castTextView.setText(movie.getActors());

        goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to go back
                finish();
            }
        });

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
         imageView = findViewById(R.id.imageView);
         titleTextView = findViewById(R.id.titleTextView);
         ratingTextView = findViewById(R.id.ratingTextView);
         durationTextView  = findViewById(R.id.durationTextView);
         genreTextView  = findViewById(R.id.genreTextView);
         plotTextView = findViewById(R.id.plotTextView);
         castTextView = findViewById(R.id.castTextView);
    }

}
