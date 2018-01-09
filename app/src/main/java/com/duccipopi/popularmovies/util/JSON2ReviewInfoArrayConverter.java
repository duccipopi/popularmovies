package com.duccipopi.popularmovies.util;

import com.duccipopi.popularmovies.moviedb.ReviewInfo;
import com.duccipopi.popularmovies.moviedb.TrailerInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ducci on 07/01/2018.
 */

public class JSON2ReviewInfoArrayConverter implements Converter<JSONObject, ArrayList<ReviewInfo>> {

    private static final String ARRAY_RESULTS = "results";

    private static final String ITEM_MOVIE_ID = "id";
    private static final String ITEM_ID = "id";
    private static final String ITEM_AUTHOR = "author";
    private static final String ITEM_CONTENT = "content";

    @Override
    public ArrayList<ReviewInfo> convert(JSONObject jsonObject) {
        ArrayList<ReviewInfo> review = new ArrayList<>();
        if (jsonObject != null) {
            try {
                int movieId = jsonObject.getInt(ITEM_MOVIE_ID);
                JSONArray results = jsonObject.getJSONArray(ARRAY_RESULTS);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    review.add(new ReviewInfo(
                            movieId,
                            item.getString(ITEM_ID),
                            item.getString(ITEM_AUTHOR),
                            item.getString(ITEM_CONTENT)));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return review;
    }
}
