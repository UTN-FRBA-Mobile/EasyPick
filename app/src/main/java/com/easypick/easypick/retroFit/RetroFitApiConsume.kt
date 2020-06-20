package com.easypick.easypick.retroFit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitApiConsume {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://c8fc1eb1-6058-403b-8189-da0d3dc64b8b.mock.pstmn.io")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();

    public fun getRetrofit(): Retrofit {
        return retrofit;
    }

}