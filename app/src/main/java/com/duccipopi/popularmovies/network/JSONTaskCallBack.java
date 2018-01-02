package com.duccipopi.popularmovies.network;

import com.duccipopi.popularmovies.network.ITaskCallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ducci on 30/12/2017.
 */

public class JSONTaskCallBack implements ITaskCallBack<String> {
    private JSONObject mJSONString;

    @Override
    public void call(String result) {
        mJSONString = getJSONObject(result);
    }

    public JSONObject getJSONObject(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
