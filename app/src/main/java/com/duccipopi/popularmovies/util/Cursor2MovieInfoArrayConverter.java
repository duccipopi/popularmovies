package com.duccipopi.popularmovies.util;

import android.database.Cursor;

import com.duccipopi.popularmovies.moviedb.MovieInfo;
import com.duccipopi.popularmovies.provider.MoviesInfoContract;

import java.util.ArrayList;

/**
 * Created by ducci on 06/01/2018.
 */

public class Cursor2MovieInfoArrayConverter implements Converter<Cursor, ArrayList<MovieInfo>> {
    @Override
    public ArrayList<MovieInfo> convert(Cursor cursor) {
        ArrayList<MovieInfo> movieInfos = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {

            do {

                movieInfos.add(new MovieInfo(
                        cursor.getInt(cursor.getColumnIndex(MoviesInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(MoviesInfoContract.MovieInfoEntry.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(MoviesInfoContract.MovieInfoEntry.COLUMN_POSTER)),
                        cursor.getString(cursor.getColumnIndex(MoviesInfoContract.MovieInfoEntry.COLUMN_OVERVIEW)),
                        cursor.getInt(cursor.getColumnIndex(MoviesInfoContract.MovieInfoEntry.COLUMN_USER_RATING)),
                        cursor.getString(cursor.getColumnIndex(MoviesInfoContract.MovieInfoEntry.COLUMN_RELEASE))));


            } while (cursor.moveToNext());

        }

        return movieInfos;
    }
}
