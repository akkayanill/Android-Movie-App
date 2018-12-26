package com.example.anilakkaya.movieapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    Movie[] movies;
    int[] IMAGES = {R.drawable.money,R.drawable.money,R.drawable.money,R.drawable.money,R.drawable.money};
    /***
     * Consructor for Adapter
     * @param context
     * @param movies
     */
    public CustomAdapter(Context context, Movie[] movies) {
        this.context = context;
        this.movies = movies;
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

        imageView.setImageResource(IMAGES[i]); //TODO : fix image issue later
        textView_title.setText(currentMovie.getTitle());
        textView_duration.setText(String.valueOf(currentMovie.getRuntime()));
        textView_rating.setText(String.valueOf(currentMovie.getRating()));
        textView_genre.setText(currentMovie.getGenre());

        return view;
    }
}
