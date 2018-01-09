package com.duccipopi.popularmovies.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.duccipopi.popularmovies.moviedb.MovieInfo;
import com.duccipopi.popularmovies.util.Cursor2MovieInfoArrayConverter;

import java.util.ArrayList;

/**
 * Created by ducci on 06/01/2018.
 */

public class MovieInfoDAO {

    public static ArrayList<MovieInfo> getAllMovieInfo(Context context) {
        Cursor cursor = context.getContentResolver().query(MoviesInfoContract.MovieInfoEntry.CONTENT_URI,
                null, null, null, null);

        ArrayList<MovieInfo> movies;
        Cursor2MovieInfoArrayConverter converter = new Cursor2MovieInfoArrayConverter();

        movies = converter.convert(cursor);
        cursor.close();

        return movies;

    }

    public static boolean contains(Context context, MovieInfo movieInfo) {
        Cursor cursor = context.getContentResolver().query(
                MoviesInfoContract.MovieInfoEntry.CONTENT_URI.buildUpon().appendPath(Integer.toString(movieInfo.getId())).build(),
                null, null, null, null);

        return cursor.getCount() > 0;
    }

    public static boolean insert(Context context, MovieInfo movieInfo) {
        ContentValues cv = new ContentValues();

        cv.put(MoviesInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID, movieInfo.getId());
        cv.put(MoviesInfoContract.MovieInfoEntry.COLUMN_TITLE, movieInfo.getOriginalTitle());
        cv.put(MoviesInfoContract.MovieInfoEntry.COLUMN_POSTER, movieInfo.getPosterThumbnail());
        cv.put(MoviesInfoContract.MovieInfoEntry.COLUMN_OVERVIEW, movieInfo.getOverview());
        cv.put(MoviesInfoContract.MovieInfoEntry.COLUMN_USER_RATING, movieInfo.getUserRating());
        cv.put(MoviesInfoContract.MovieInfoEntry.COLUMN_RELEASE, movieInfo.getRelease());

        return context.getContentResolver().insert(MoviesInfoContract.MovieInfoEntry.CONTENT_URI, cv) != null;
    }

    public static boolean delete(Context context, MovieInfo movieInfo) {
        return context.getContentResolver().delete(
                MoviesInfoContract.MovieInfoEntry.CONTENT_URI.buildUpon().appendPath(Integer.toString(movieInfo.getId())).build(),
                null, null) > 0;
    }
}
