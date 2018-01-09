package com.duccipopi.popularmovies.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MoviesInfoProvider extends ContentProvider {

    public static final int MOVIEINFO_DIRECTORY = 100;
    public static final int MOVIEINFO_ITEM = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MoviesInfoContract.AUTHORITY, MoviesInfoContract.PATH_MOVIEINFO, MOVIEINFO_DIRECTORY);
        uriMatcher.addURI(MoviesInfoContract.AUTHORITY, MoviesInfoContract.PATH_MOVIEINFO_ITEM, MOVIEINFO_ITEM);

        return uriMatcher;
    }


    private MoviesInfoDBHelper mDBHelper;

    public MoviesInfoProvider() {
    }

    @Override
    public boolean onCreate() {

        mDBHelper = new MoviesInfoDBHelper(getContext());

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        String querySelection = null;
        String[] queryArgs = null;

        switch (sUriMatcher.match(uri)) {
            case MOVIEINFO_DIRECTORY:
                break;
            case MOVIEINFO_ITEM:
                querySelection = MoviesInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID + "=?";
                queryArgs = new String[]{uri.getLastPathSegment()};
                break;
            default:
                throw new UnsupportedOperationException("Invalid URI");
        }

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        getContext().getContentResolver().notifyChange(uri, null);

        return db.delete(MoviesInfoContract.MovieInfoEntry.TABLE_NAME,
                querySelection,
                queryArgs);

    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIEINFO_DIRECTORY:
                return MoviesInfoContract.MIME_DIRECTORY;
            case MOVIEINFO_ITEM:
                return MoviesInfoContract.PATH_MOVIEINFO_ITEM;
            default:
                throw new UnsupportedOperationException("Invalid URI");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long id = db.insert(MoviesInfoContract.MovieInfoEntry.TABLE_NAME, null, values);

        Uri returnUri = MoviesInfoContract.MovieInfoEntry.CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();

        getContext().getContentResolver().notifyChange(returnUri, null);

        return returnUri;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        String querySelection = null;
        String[] queryArgs = null;

        switch (sUriMatcher.match(uri)) {
            case MOVIEINFO_DIRECTORY:
                break;
            case MOVIEINFO_ITEM:
                querySelection = MoviesInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID + "=?";
                queryArgs = new String[]{uri.getLastPathSegment()};
                break;
            default:
                throw new UnsupportedOperationException("Invalid URI");
        }

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor cursor = db.query(MoviesInfoContract.MovieInfoEntry.TABLE_NAME,
                MoviesInfoContract.MovieInfoEntry.COLUMNS,
                querySelection,
                queryArgs,
                null,
                null,
                null);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        String querySelection = null;
        String[] queryArgs = null;

        switch (sUriMatcher.match(uri)) {
            case MOVIEINFO_DIRECTORY:
                return -1;
            case MOVIEINFO_ITEM:
                querySelection = MoviesInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID + "=?";
                queryArgs = new String[]{uri.getLastPathSegment()};
                break;
            default:
                throw new UnsupportedOperationException("Invalid URI");
        }

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        return db.update(MoviesInfoContract.MovieInfoEntry.TABLE_NAME,
                values,
                querySelection,
                queryArgs);

    }
}
