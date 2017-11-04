package com.example.basi.test.api;

import com.example.basi.test.model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by basi on 13/2/17.
 */

public interface MovieApi {

    @GET("list_movies.json")
    Call<MovieList> getMovies(
            @Query("limit") int limit,
            @Query("sort") String seeds
    );

    @GET("list_movies.json")
    Observable<MovieList> getMoviesBySearch(
            @Query("query_term") String movie);
}
