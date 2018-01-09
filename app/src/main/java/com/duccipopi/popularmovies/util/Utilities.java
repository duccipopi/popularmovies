package com.duccipopi.popularmovies.util;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.duccipopi.popularmovies.moviedb.MovieInfo;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by ducci on 30/12/2017.
 */

public final class Utilities {

    public enum IMAGE_SOURCE {
        LOREM_PIXEL,
        LOREM_FLICKER
    }



    static Random random = new Random();

    public static int getRandomColor(int variation) {

        return Color.argb(0, (random.nextInt() + variation) % 255,
                (random.nextInt() + variation) % 255,
                (random.nextInt() + variation) % 255);
    }

    public static void loadImageByString(Context context, String url, ImageView holder) {
        Picasso.with(context).load(url).into(holder);
        Picasso.with(context).setLoggingEnabled(true);
    }

    public static void loadImageByURL(Context context, URL url, ImageView holder) {
        loadImageByString(context, url.toString(), holder);
    }

    public static ArrayList<MovieInfo> getRandomMovies(int maxItems) {

        ArrayList<MovieInfo> movieInfoArrayList = new ArrayList<>(maxItems);

        for (int i = 0; i < maxItems; i++) {
            movieInfoArrayList.add(new MovieInfo((int) Math.random(),
                    getRandomName(),
                    getRandomImageURL(IMAGE_SOURCE.LOREM_FLICKER),
                    getRandomText(maxItems*10),
                    (int) Math.random(),
                    (new Date()).toString()));
        }

        return movieInfoArrayList;
    }

    private static String getRandomText(int length) {
        StringBuilder builder = new StringBuilder(length);

        while (builder.length() < length) {
            builder.append(getRandomName());
        }

        return builder.toString();
    }

    private static String getRandomImageURL(IMAGE_SOURCE source) {
        String url;

        switch (source) {
            case LOREM_PIXEL:
                url = "http://lorempixel.com/440/662/abstract/" + random.nextInt();
                break;
            case LOREM_FLICKER:
                url = "https://loremflickr.com/320/240?random=" + random.nextInt();
                break;
            default:
                url = null;
        }

        return url;
    }

    private static String getRandomName() {
        return UUID.randomUUID().toString();
    }
}
