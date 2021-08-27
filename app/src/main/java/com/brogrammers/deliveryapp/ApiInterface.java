package com.brogrammers.deliveryapp;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("maps/api/distancematrix/json")
    Single<Result> getDistance(@Query("key") String key,
                               @Query("origins") String origins,
                               @Query("destinations") String destinations);
}
