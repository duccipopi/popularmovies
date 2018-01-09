package com.duccipopi.popularmovies.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.duccipopi.popularmovies.R;
import com.duccipopi.popularmovies.moviedb.MovieDBURLHelper;
import com.duccipopi.popularmovies.moviedb.TrailerInfo;

/**
 * Created by ducci on 07/01/2018.
 */

public class TrailerViewInflater implements ViewInflater<TrailerInfo> {
    @Override
    public View inflate(final Context context, final TrailerInfo trailerInfo) {
        View v = null;

        if (context != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.trailer_card_item, null);

            TextView title = v.findViewById(R.id.card_trailer_name);
            title.setText(trailerInfo.getName());

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openTrailer = new Intent(Intent.ACTION_VIEW, MovieDBURLHelper.getTrailerViewUri(trailerInfo.getKey()));

                    if (openTrailer.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(openTrailer);
                    }
                }
            });

        }

        return v;
    }
}
