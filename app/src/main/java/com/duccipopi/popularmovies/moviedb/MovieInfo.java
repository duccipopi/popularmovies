package com.duccipopi.popularmovies.moviedb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ducci on 30/12/2017.
 */

public class MovieInfo implements Parcelable{

    private String originalTitle;
    private String posterThumbnail;
    private String overview;
    private int userRating;
    private String release;

    public MovieInfo(String originalTitle, String posterThumbnail, String overview, int userRating, String release) {
        this.originalTitle = originalTitle;
        this.posterThumbnail = posterThumbnail;
        this.overview = overview;
        this.userRating = userRating;
        this.release = release;
    }

    protected MovieInfo(Parcel in) {
        originalTitle = in.readString();
        posterThumbnail = in.readString();
        overview = in.readString();
        userRating = in.readInt();
        release = in.readString();
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterThumbnail() {
        return posterThumbnail;
    }

    public String getOverview() {
        return overview;
    }

    public int getUserRating() {
        return userRating;
    }

    public String getRelease() {
        return release;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(originalTitle);
        parcel.writeString(posterThumbnail);
        parcel.writeString(overview);
        parcel.writeInt(userRating);
        parcel.writeString(release);
    }
}
