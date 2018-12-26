package com.example.anilakkaya.movieapp;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {
    @SerializedName("Title")
    private String title;
    @SerializedName("Released")
    private String release;
    @SerializedName("Runtime")
    private String runtime;
    @SerializedName("Plot")
    private String plot;
    @SerializedName("Genre")
    private String genre;
    @SerializedName("Director")
    private String director;
    @SerializedName("Poster")
    private String image;
    @SerializedName("imdbRating")
    private Double rating;
    @SerializedName("Actors")
    private String actors;

    public Movie(String title, String release, String runtime, String genre, String director, String image, Double rating) {
        this.title = title;
        this.release = release;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.image = image;
        this.rating = rating;
    }
    public Movie(String title, String release, String plot, String runtime, String genre, String director, String image, Double rating, String actors) {
        this.title = title;
        this.release = release;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.image = image;
        this.rating = rating;
        this.plot = plot;
        this.actors = actors;
    }
    public String getTitle() {
        return title;
    }
    public String getReleaseDate() {
        return release;
    }
    public String getRuntime() {
        return runtime;
    }
    public String getGenre() {
        return genre;
    }
    public String getDirector() { return director; }

    public String getActors() { return actors; }

    public String getPlot() { return plot; }
    public String getImage() {
        return image;
    }
    public Double getRating() {
        return rating;
    }
    public String toString() {
        return title + " " + release + " ";
    }
}
