package com.duccipopi.popularmovies.util;

import com.duccipopi.popularmovies.moviedb.TrailerInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ducci on 07/01/2018.
 */

public class JSON2TrailerInfoArrayConverter implements Converter<JSONObject, ArrayList<TrailerInfo>> {

    private static final String ARRAY_RESULTS = "results";

    private static final String ITEM_MOVIE_ID = "id";
    private static final String ITEM_ID = "id";
    private static final String ITEM_KEY = "key";
    private static final String ITEM_NAME = "name";


    @Override
    public ArrayList<TrailerInfo> convert(JSONObject jsonObject) {
        ArrayList<TrailerInfo> trailer = new ArrayList<>();
        if (jsonObject != null) {
            try {
                int movieId = jsonObject.getInt(ITEM_MOVIE_ID);
                JSONArray results = jsonObject.getJSONArray(ARRAY_RESULTS);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    trailer.add(new TrailerInfo(
                            movieId,
                            item.getString(ITEM_ID),
                            item.getString(ITEM_KEY),
                            item.getString(ITEM_NAME)));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return trailer;
    }
}
