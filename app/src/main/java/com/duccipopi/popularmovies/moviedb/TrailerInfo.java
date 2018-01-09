package com.duccipopi.popularmovies.moviedb;

/**
 * Created by ducci on 07/01/2018.
 */

public class TrailerInfo {
    private int movieId;
    private String id;
    private String key;
    private String name;

    public TrailerInfo(int movieId, String id, String key, String name) {
        this.movieId = movieId;
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
