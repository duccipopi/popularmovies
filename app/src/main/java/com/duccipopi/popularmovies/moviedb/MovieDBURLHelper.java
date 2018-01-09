package com.duccipopi.popularmovies.moviedb;

import android.net.Uri;

import com.duccipopi.popularmovies.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ducci on 30/12/2017.
 */

public class MovieDBURLHelper {

    // The Movie DB API Key
    private static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    // Movie Query URL
    private static final String BASE_MOVIE_QUERY_URL = "http://api.themoviedb.org/3/movie/";
    private static final String MOVIE_QUERY_POPULAR = "popular";
    private static final String MOVIE_QUERY_TOP_RATED = "top_rated";

    private static final String MOVIE_QUERY_TRAILERS = "videos";
    private static final String MOVIE_QUERY_REVIEWS = "reviews";

    private static final String MOVIE_QUERY_ARG_API_KEY = "api_key";
    private static final String MOVIE_QUERY_ARG_PAGE = "page";

    // Movie Poster URL
    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
    private static final String POSTER_IMAGE_SIZE = "w185";
    private static final int MOVIE_QUERY_DEFAULT_PAGE = 1;

    // Trailer URL
    private static final String BASE_TRAILER_URL = "https://www.youtube.com/watch";
    private static final String TRAILER_ARG_KEY = "v";

    public static URL getMovieQueryURL(boolean popularMovies) throws MalformedURLException {
        return getMovieQueryURL(popularMovies, MOVIE_QUERY_DEFAULT_PAGE);
    }

    public static URL getMovieQueryURL(boolean popularMovies, int page) throws MalformedURLException {
        Uri uri = Uri.parse(BASE_MOVIE_QUERY_URL).buildUpon()
                .appendPath(popularMovies ? MOVIE_QUERY_POPULAR : MOVIE_QUERY_TOP_RATED)
                .appendQueryParameter(MOVIE_QUERY_ARG_API_KEY, API_KEY)
                .appendQueryParameter(MOVIE_QUERY_ARG_PAGE, String.valueOf(page))
                .build();

        return new URL(uri.toString());
    }

    public static URL getMoviePosterURL(String path) throws MalformedURLException {
        Uri uri = Uri.parse(BASE_POSTER_URL).buildUpon()
                .appendPath(POSTER_IMAGE_SIZE)
                .appendEncodedPath(path)
                .build();

        return new URL(uri.toString());
    }

    public static URL getMovieTrailersQueryURL(int id) throws MalformedURLException {
        Uri uri = Uri.parse(BASE_MOVIE_QUERY_URL).buildUpon()
                .appendPath(Integer.toString(id))
                .appendPath(MOVIE_QUERY_TRAILERS)
                .appendQueryParameter(MOVIE_QUERY_ARG_API_KEY, API_KEY)
                .build();

        return new URL(uri.toString());
    }

    public static URL getMovieReviewsQueryURL(int id) throws MalformedURLException {
        Uri uri = Uri.parse(BASE_MOVIE_QUERY_URL).buildUpon()
                .appendPath(Integer.toString(id))
                .appendPath(MOVIE_QUERY_REVIEWS)
                .appendQueryParameter(MOVIE_QUERY_ARG_API_KEY, API_KEY)
                .build();

        return new URL(uri.toString());
    }

    public static Uri getTrailerViewUri(String key) {
        Uri uri = Uri.parse(BASE_TRAILER_URL).buildUpon()
                .appendQueryParameter(TRAILER_ARG_KEY, key)
                .build();

        return uri;
    }
}
