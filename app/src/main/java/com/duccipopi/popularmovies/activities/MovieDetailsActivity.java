package com.duccipopi.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.duccipopi.popularmovies.R;
import com.duccipopi.popularmovies.moviedb.MovieDBURLHelper;
import com.duccipopi.popularmovies.moviedb.MovieInfo;
import com.duccipopi.popularmovies.util.Utilities;

import java.net.MalformedURLException;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView ivPoster;
    TextView tvTitle;
    TextView tvReleaseDate;
    TextView tvUserRating;
    TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        setTitle(getString(R.string.title_movie_details));

        // Get movies info
        Intent movieIntent = getIntent();

        if (!movieIntent.hasExtra(ActivitiesContract.EXTRA_MOVIE_INFO))
            finishActivity(ActivitiesContract.REQUEST_CODE_FAIL_NO_MOVIE_INFO);

        MovieInfo movieInfo = movieIntent.getParcelableExtra(ActivitiesContract.EXTRA_MOVIE_INFO);

        // Get views
        ivPoster = findViewById(R.id.iv_details_poster);
        tvTitle = findViewById(R.id.tv_details_title);
        tvReleaseDate = findViewById(R.id.tv_details_release_date);
        tvUserRating = findViewById(R.id.tv_details_user_rating);
        tvOverview = findViewById(R.id.tv_details_overview);

        // Set values
        try {
            Utilities.loadImageByURL(this, MovieDBURLHelper.getMoviePosterURL(movieInfo.getPosterThumbnail()), ivPoster);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        tvTitle.setText(movieInfo.getOriginalTitle());
        tvReleaseDate.setText(movieInfo.getRelease());
        tvUserRating.setText(String.valueOf(movieInfo.getUserRating()));
        tvOverview.setText(movieInfo.getOverview());

    }
}
