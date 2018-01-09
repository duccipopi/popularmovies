package com.duccipopi.popularmovies.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ducci on 03/01/2018.
 */

public class MoviesInfoContract {

    public static final String AUTHORITY = "com.duccipopi.popularmovies.movieinfo";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIEINFO = "movieinfo";
    public static final String PATH_MOVIEINFO_ITEM = PATH_MOVIEINFO + "/#";

    public static final String MIME_DIRECTORY = "movieinfo/directory";
    public static final String MIME_ITEM = "movieinfo/item";

    public static final class MovieInfoEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIEINFO).build();

        public static final String TABLE_NAME = "movieinfo";

        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_RELEASE = "release";


        public static final String[] COLUMNS = {
                COLUMN_MOVIE_ID,
                COLUMN_TITLE,
                COLUMN_POSTER,
                COLUMN_OVERVIEW,
                COLUMN_USER_RATING,
                COLUMN_RELEASE
        };

    }
}
