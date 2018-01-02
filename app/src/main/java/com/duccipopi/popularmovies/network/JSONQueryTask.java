package com.duccipopi.popularmovies.network;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ducci on 30/12/2017.
 */
public class JSONQueryTask extends AsyncTask<URL, Void, String> {
    private ITaskCallBack<String> mCallback;

    public JSONQueryTask(ITaskCallBack<String> callBack) {
        this.mCallback = callBack;
    }

    @Override
    protected String doInBackground(URL... urls) {
        URL queryURL = urls[0];
        StringBuilder queryResults = new StringBuilder();

        try {
            HttpURLConnection connection = (HttpURLConnection) queryURL.openConnection();
            InputStream response = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));

            String line = null;

            while ((line = reader.readLine()) != null) {
                queryResults.append(line);
            }

            connection.disconnect();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return queryResults.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        mCallback.call(s);
    }
}
