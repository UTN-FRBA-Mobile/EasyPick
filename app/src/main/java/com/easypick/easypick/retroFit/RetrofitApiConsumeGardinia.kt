package com.easypick.easypick.retroFit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiConsumeGardinia {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://gardinia.online/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();

    public fun getRetrofit(): Retrofit {
        return retrofit;
    }
}