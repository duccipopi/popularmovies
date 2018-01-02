package com.duccipopi.popularmovies.activities;

/**
 * Created by ducci on 31/12/2017.
 */

public interface ActivitiesContract {

    int REQUEST_CODE_SUCCESS = 0;
    int REQUEST_CODE_FAIL_NO_INTENT = 1;
    int REQUEST_CODE_FAIL_NO_MOVIE_INFO = 2;

    String EXTRA_MOVIE_INFO = "movie_info";

}
