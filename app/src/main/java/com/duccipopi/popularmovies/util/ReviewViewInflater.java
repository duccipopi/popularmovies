package com.duccipopi.popularmovies.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.duccipopi.popularmovies.R;
import com.duccipopi.popularmovies.moviedb.ReviewInfo;

/**
 * Created by ducci on 07/01/2018.
 */

public class ReviewViewInflater implements ViewInflater<ReviewInfo> {
    @Override
    public View inflate(Context context, ReviewInfo reviewInfo) {
        View v = null;

        if (context != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.review_card_item, null);

            TextView name = v.findViewById(R.id.card_review_name);
            name.setText(reviewInfo.getAuthor());

            TextView review = v.findViewById(R.id.card_review_content);
            review.setText(reviewInfo.getContent());


        }

        return v;
    }
}
