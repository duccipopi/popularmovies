package com.duccipopi.popularmovies.util;

import android.content.Context;
import android.view.View;

/**
 * Created by ducci on 07/01/2018.
 */

public interface ViewInflater<T> {
    
    View inflate(Context context, T t);
}
