package com.easypick.easypick.retroFit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitApiConsume {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://36f52c82-1e21-4f33-a359-1914068f7489.mock.pstmn.io"gi)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();

    public fun getRetrofit(): Retrofit {
        return retrofit;
    }

}