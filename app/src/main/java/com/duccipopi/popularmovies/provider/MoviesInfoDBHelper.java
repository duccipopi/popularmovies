package com.duccipopi.popularmovies.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ducci on 03/01/2018.
 */

class MoviesInfoDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movieinfo.db";
    private static final int DATABASE_VERSION = 6;

    private static final String SQL_CREATE_MOVIEINFO_TABLE = "CREATE TABLE " +
            MoviesInfoContract.MovieInfoEntry.TABLE_NAME + " (" +
            MoviesInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY, " +
            MoviesInfoContract.MovieInfoEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            MoviesInfoContract.MovieInfoEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
            MoviesInfoContract.MovieInfoEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "  +
            MoviesInfoContract.MovieInfoEntry.COLUMN_USER_RATING + " INTEGER NOT NULL, " +
            MoviesInfoContract.MovieInfoEntry.COLUMN_RELEASE + " TEXT NOT NULL" +
            ");";

    private static final String SQL_DROP_MOVIEINFO_TABLE = "DROP TABLE IF EXISTS " +
            MoviesInfoContract.MovieInfoEntry.TABLE_NAME +
            ";";



    public MoviesInfoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIEINFO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP_MOVIEINFO_TABLE);
        onCreate(sqLiteDatabase);
    }
}
