package com.duccipopi.popularmovies.provider;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.duccipopi.popularmovies.moviedb.MovieInfo;
import com.duccipopi.popularmovies.util.ITaskCallBack;

import java.util.ArrayList;

/**
 * Created by ducci on 06/01/2018.
 */

public class MovieInfoDBQueryTask extends AsyncTask<Void, Void, ArrayList<MovieInfo>> {
    private ITaskCallBack<ArrayList<MovieInfo>> mCallBack;
    private Context mContext;

    public MovieInfoDBQueryTask(Context mContext, ITaskCallBack<ArrayList<MovieInfo>> mCallBack) {
        this.mCallBack = mCallBack;
        this.mContext = mContext;
    }

    @Override
    protected ArrayList<MovieInfo> doInBackground(Void... voids) {
        return MovieInfoDAO.getAllMovieInfo(mContext);
    }

    @Override
    protected void onPostExecute(ArrayList<MovieInfo> movieInfos) {
        mCallBack.call(movieInfos);
    }
}
