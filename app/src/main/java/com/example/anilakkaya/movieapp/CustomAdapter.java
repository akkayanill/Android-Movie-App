package com.example.anilakkaya.movieapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    Movie[] movies;
    private ArrayList<Movie> favoriteMovies;
//    int[] IMAGES = {R.drawable.money,R.drawable.money,R.drawable.money,R.drawable.money,R.drawable.money};


    /***
     * Consructor for Adapter
     * @param context
     * @param favoriteMovies
     */
    //public CustomAdapter(Context context, ArrayList<Movie> movies) {
    public CustomAdapter(Context context, ArrayList<Movie> favoriteMovies) {
        this.context = context;
//        this.movies = movies;
        this.favoriteMovies = favoriteMovies;
        movies = favoriteMovies.toArray(new Movie[favoriteMovies.size()]);
    }

    @Override
    public int getCount() { return movies.length; }
//    @Override
//    public Object getItem(int position) { return movies[position]; } //TODO: handle  getItem
    @Override
    public Object getItem(int position) { return movies[position]; } //TODO: handle  getItem
    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_cell, viewGroup, false);
        }

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView_title = view.findViewById(R.id.titleTextView);
        TextView textView_genre = view.findViewById(R.id.genreTextView);
        TextView textView_rating = view.findViewById(R.id.ratingTextView);
        TextView textView_duration = view.findViewById(R.id.durationTextView);

        Movie currentMovie = movies[i];

        new CustomAdapter.DownloadImageTask(imageView).execute(currentMovie.getImage());
        textView_title.setText(currentMovie.getTitle());
        textView_duration.setText(String.valueOf(currentMovie.getRuntime()));
        textView_rating.setText(String.valueOf(currentMovie.getRating()));
        textView_genre.setText(currentMovie.getGenre());

        return view;
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
}
