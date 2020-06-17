package com.easypick.easypick.retroFit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitApiConsume {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("MODIFICAR_URL")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    public fun getRetrofit(): Retrofit {
        return retrofit;
    }

}