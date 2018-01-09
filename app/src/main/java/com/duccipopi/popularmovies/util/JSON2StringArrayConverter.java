package com.duccipopi.popularmovies.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ducci on 06/01/2018.
 */

public class JSON2StringArrayConverter implements Converter<JSONObject, ArrayList<String>> {

    private static final String ARRAY_RESULTS = "results";

    private String mItem;

    public JSON2StringArrayConverter(String mItem) {
        this.mItem = mItem;
    }

    @Override
    public ArrayList<String> convert(JSONObject jsonObject) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (jsonObject != null) {
            try {
                JSONArray results = jsonObject.getJSONArray(ARRAY_RESULTS);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    arrayList.add(item.getString(mItem));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }
}
