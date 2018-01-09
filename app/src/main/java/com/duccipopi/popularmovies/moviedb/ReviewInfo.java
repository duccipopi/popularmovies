package com.duccipopi.popularmovies.moviedb;

/**
 * Created by ducci on 07/01/2018.
 */

public class ReviewInfo {
    private int movieId;
    private String id;
    private String author;
    private String content;

    public ReviewInfo(int movieId, String id, String author, String content) {
        this.movieId = movieId;
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
