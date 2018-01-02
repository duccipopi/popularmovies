package com.duccipopi.popularmovies.util;

import com.duccipopi.popularmovies.moviedb.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ducci on 30/12/2017.
 */

public class JSON2MovieInfoArrayConverter extends Converter<JSONObject, ArrayList<MovieInfo>> {

    private static final String ARRAY_RESULTS = "results";

    private static final String ITEM_ORIGINAL_TITLE = "original_title";
    private static final String ITEM_POSTER = "poster_path";
    private static final String ITEM_OVERVIEW = "overview";
    private static final String ITEM_USER_RATING = "vote_average";
    private static final String ITEM_RELEASE_DATE = "release_date";


    @Override
    public ArrayList<MovieInfo> convert(JSONObject jsonObject) {
        ArrayList<MovieInfo> movies = new ArrayList<>();
        if (jsonObject != null) {
            try {
                JSONArray results = jsonObject.getJSONArray(ARRAY_RESULTS);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    movies.add(new MovieInfo(
                            item.getString(ITEM_ORIGINAL_TITLE),
                            item.getString(ITEM_POSTER),
                            item.getString(ITEM_OVERVIEW),
                            item.getInt(ITEM_USER_RATING),
                            item.getString(ITEM_RELEASE_DATE)
                    ));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
}
