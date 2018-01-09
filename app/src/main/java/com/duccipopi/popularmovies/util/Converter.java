package com.duccipopi.popularmovies.util;

/**
 * Created by ducci on 30/12/2017.
 */

public interface Converter<T,S> {

    S convert(T t);

}
