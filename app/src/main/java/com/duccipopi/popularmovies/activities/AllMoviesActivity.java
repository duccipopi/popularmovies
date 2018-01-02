package com.duccipopi.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.duccipopi.popularmovies.R;
import com.duccipopi.popularmovies.moviedb.MovieInfo;
import com.duccipopi.popularmovies.moviedb.MoviePosterAdapter;
import com.duccipopi.popularmovies.network.ITaskCallBack;
import com.duccipopi.popularmovies.network.JSONQueryTask;
import com.duccipopi.popularmovies.util.JSON2MovieInfoArrayConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

import static com.duccipopi.popularmovies.moviedb.MovieDBURLHelper.getMovieQueryURL;

public class AllMoviesActivity extends AppCompatActivity implements ITaskCallBack<String> {

    private static final int MAX_COLUMNS = 5;
    private static final String SAVED_INSTANCE_BUNDLE = "movies";

    private static boolean popularMoviesFilter = true;

    RecyclerView allMoviesList;
    MoviePosterAdapter moviePosterAdapter;

    FloatingActionButton fab_filter;

    ArrayList<MovieInfo> movies;
    ArrayList<String> moviesPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);

        // Floating button defines filter
        fab_filter = findViewById(R.id.fab_filter);
        fab_filter.setOnClickListener(new FilterOnClickListener(this));

        updateTitle(popularMoviesFilter);

        // Recycler view init
        allMoviesList = findViewById(R.id.rv_movies_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, MAX_COLUMNS);
        allMoviesList.setLayoutManager(gridLayoutManager);
        allMoviesList.setHasFixedSize(true);

        if(savedInstanceState == null
                || !savedInstanceState.containsKey(SAVED_INSTANCE_BUNDLE)
                || savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_BUNDLE) == null) {
            movieDBQuery(this, popularMoviesFilter);
        }
        else {
            movies = savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_BUNDLE);
            moviesPoster = extractPoster(movies);

            moviePosterAdapter = new MoviePosterAdapter(this, moviesPoster, new PosterOnClickListener(this));
            allMoviesList.setAdapter(moviePosterAdapter);
        }

    }

    private void movieDBQuery(ITaskCallBack callBack, boolean popularMovies) {
        // Start Async task to gather Movies DB info
        JSONQueryTask queryTask = new JSONQueryTask(callBack);
        try {
            queryTask.execute(getMovieQueryURL(popularMovies));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> extractPoster(ArrayList<MovieInfo> movies) {
        ArrayList<String> posters = new ArrayList<>(movies.size());

        for (MovieInfo movie : movies) {
            posters.add(movie.getPosterThumbnail());
        }

        return posters;
    }

    public void updateTitle(boolean popularMovies) {
        setTitle(popularMovies ? R.string.popular_movies : R.string.top_rated_movies);
    }

    @Override
    public void call(String result) {

        JSONObject query = null;
        try {
            query = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSON2MovieInfoArrayConverter converter = new JSON2MovieInfoArrayConverter();
        // Movie info gathering
        movies = converter.convert(query);
        moviesPoster = extractPoster(movies);

        moviePosterAdapter = new MoviePosterAdapter(this, moviesPoster, new PosterOnClickListener(this));

        allMoviesList.setAdapter(moviePosterAdapter);

    }

    private class FilterOnClickListener implements View.OnClickListener {

        private ITaskCallBack mCallBack;

        public FilterOnClickListener(ITaskCallBack callBack) {
            this.mCallBack = callBack;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof FloatingActionButton) {
                popularMoviesFilter = !popularMoviesFilter;

                ((FloatingActionButton) view).setImageLevel(popularMoviesFilter ? 0 : 1);

                updateTitle(popularMoviesFilter);

                movieDBQuery(mCallBack, popularMoviesFilter);
            }
        }
    }

    private class PosterOnClickListener implements MoviePosterAdapter.ListItemClickListener {

        private final Context mContext;

        public PosterOnClickListener(Context context) {
            this.mContext = context;
        }

        @Override
        public void onListItemClick(int index) {
            Intent detailsIntent = new Intent(mContext, MovieDetailsActivity.class);
            detailsIntent.putExtra(ActivitiesContract.EXTRA_MOVIE_INFO, movies.get(index));
            mContext.startActivity(detailsIntent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVED_INSTANCE_BUNDLE, movies);
        super.onSaveInstanceState(outState);
    }
}
