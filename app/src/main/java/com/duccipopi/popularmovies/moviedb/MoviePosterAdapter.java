package com.duccipopi.popularmovies.moviedb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.duccipopi.popularmovies.R;
import com.duccipopi.popularmovies.util.Utilities;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by ducci on 30/12/2017.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder> {

    private ArrayList<String> moviesPosterArray;
    private Context mContext;
    private ListItemClickListener mListItemClickListener;

    public MoviePosterAdapter(Context context, ArrayList<String> moviesPosterArray, ListItemClickListener listener) {
        this.moviesPosterArray = moviesPosterArray;
        this.mContext = context;
        this.mListItemClickListener = listener;
    }

    @Override
    public MoviePosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //mContext = parent.getContext();
        int itemLayout = R.layout.movie_poster_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View item = inflater.inflate(itemLayout, parent, false);
        MoviePosterViewHolder viewHolder = new MoviePosterViewHolder(item);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviePosterViewHolder holder, int position) {
        holder.bind(mContext, position);
    }

    @Override
    public int getItemCount() {
        return moviesPosterArray.size();
    }

    class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView listItemMoviePoster;

        public MoviePosterViewHolder(View itemView) {
            super(itemView);

            listItemMoviePoster = itemView.findViewById(R.id.iv_item_movie_poster);
            itemView.setOnClickListener(this);
        }

        public void bind(Context context, int position) {
            try {
                Utilities.loadImageByURL(context, MovieDBURLHelper.getMoviePosterURL(moviesPosterArray.get(position)), listItemMoviePoster);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            mListItemClickListener.onListItemClick(index);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int index);
    }
}
