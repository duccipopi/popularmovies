package com.duccipopi.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.duccipopi.popularmovies.R;
import com.duccipopi.popularmovies.moviedb.MovieDBURLHelper;
import com.duccipopi.popularmovies.moviedb.MovieInfo;
import com.duccipopi.popularmovies.network.JSONQueryTask;
import com.duccipopi.popularmovies.provider.MovieInfoDAO;
import com.duccipopi.popularmovies.util.Converter;
import com.duccipopi.popularmovies.util.ITaskCallBack;
import com.duccipopi.popularmovies.util.JSON2ReviewInfoArrayConverter;
import com.duccipopi.popularmovies.util.JSON2TrailerInfoArrayConverter;
import com.duccipopi.popularmovies.util.ReviewViewInflater;
import com.duccipopi.popularmovies.util.TrailerViewInflater;
import com.duccipopi.popularmovies.util.Utilities;
import com.duccipopi.popularmovies.util.ViewInflater;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String SAVED_INSTANCE_TRAILERS = "trailers";
    private static final String SAVED_INSTANCE_REVIEWS = "reviews";


    ImageView ivPoster;
    TextView tvTitle;
    TextView tvReleaseDate;
    TextView tvUserRating;
    TextView tvOverview;

    ViewGroup trailers;
    ViewGroup reviews;

    CheckBox cbFavorite;

    MovieInfo movieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        setTitle(getString(R.string.title_movie_details));

        // Get movies info
        Intent movieIntent = getIntent();

        if (!movieIntent.hasExtra(ActivitiesContract.EXTRA_MOVIE_INFO))
            finishActivity(ActivitiesContract.REQUEST_CODE_FAIL_NO_MOVIE_INFO);

        movieInfo = movieIntent.getParcelableExtra(ActivitiesContract.EXTRA_MOVIE_INFO);

        // Get views
        ivPoster = findViewById(R.id.iv_details_poster);
        tvTitle = findViewById(R.id.tv_details_title);
        tvReleaseDate = findViewById(R.id.tv_details_release_date);
        tvUserRating = findViewById(R.id.tv_details_user_rating);
        tvOverview = findViewById(R.id.tv_details_overview);

        trailers = findViewById(R.id.rv_details_trailers);
        reviews = findViewById(R.id.rv_details_reviews);

        cbFavorite = findViewById(R.id.cb_favorite);

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

        cbFavorite.setChecked(isFavorite(movieInfo));

        // Set favorite click listener
        cbFavorite.setOnClickListener(new FavoriteClickListener(this));

        // Query for trailers and reviews
        // Fill Trailers
        try {
            JSONQueryTask queryTask = new JSONQueryTask(new CallBackUpdater(this, trailers, new TrailerViewInflater(),
                    new JSON2TrailerInfoArrayConverter()));
            queryTask.execute(MovieDBURLHelper.getMovieTrailersQueryURL(movieInfo.getId()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Fill Reviews
        try {
            JSONQueryTask queryTask = new JSONQueryTask(new CallBackUpdater(this, reviews, new ReviewViewInflater(),
                    new JSON2ReviewInfoArrayConverter()));
            queryTask.execute(MovieDBURLHelper.getMovieReviewsQueryURL(movieInfo.getId()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


//        try {
//            getExtraData(trailers, MovieDBURLHelper.getMovieTrailersQueryURL(movieInfo.getId()),
//                    savedInstanceState, SAVED_INSTANCE_TRAILERS);
//            getExtraData(reviews, MovieDBURLHelper.getMovieReviewsQueryURL(movieInfo.getId()),
//                    savedInstanceState, SAVED_INSTANCE_REVIEWS);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

    }

    private void getExtraData(ListView listView, ArrayAdapter adapter, String item, URL queryURL,
                              Bundle savedInstanceState, String savedInstanceKey) {
        if (savedInstanceState != null
                && savedInstanceState.containsKey(savedInstanceKey)
                && savedInstanceState.getParcelableArrayList(savedInstanceKey) != null) {
            adapter.clear();
            adapter.addAll(savedInstanceState.getParcelableArrayList(savedInstanceKey));
            listView.setAdapter(adapter);
        } else {
            //JSONQueryTask queryTask = new JSONQueryTask(new CallBackUpdater(listView, adapter, item));
            //queryTask.execute(queryURL);
        }
    }

    private boolean isFavorite(MovieInfo movieInfo) {
        return (MovieInfoDAO.contains(this, movieInfo));
    }

    private class FavoriteClickListener implements View.OnClickListener {

        private final Context mContext;

        public FavoriteClickListener(Context context) {
            this.mContext = context;
        }

        @Override
        public void onClick(View view) {

            if (((CheckBox) view).isChecked()) {
                MovieInfoDAO.insert(mContext, movieInfo);
            } else
                MovieInfoDAO.delete(mContext, movieInfo);
        }
    }

    private class CallBackUpdater implements ITaskCallBack<String> {

        private Context mContext;
        private ViewGroup mViewGroup;
        private ViewInflater mInflater;
        private Converter mConverter;

        private CallBackUpdater(Context context, ViewGroup viewGroup, ViewInflater inflater,
                                Converter converter) {
            mContext = context;
            mViewGroup = viewGroup;
            mInflater = inflater;
            mConverter = converter;
        }

        @Override
        public void call(String result) {

            JSONObject query = null;
            try {
                query = new JSONObject((String) result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList resultArray = (ArrayList)mConverter.convert(query);

            for (Object item: resultArray) {
                mViewGroup.addView(mInflater.inflate(mContext, item));
            }

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private ArrayList<? extends Parcelable> retrieveItemsFromAdapter(ListAdapter adapter) {

        if (adapter != null && adapter.getCount() > 0) {

            ArrayList items = new ArrayList(adapter.getCount());
            for (int i = 0; i < adapter.getCount(); i++)
                items.add(adapter.getItem(i));

            return items;
        }

        return null;
    }

}
