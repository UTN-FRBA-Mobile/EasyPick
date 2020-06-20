package com.easypick.easypick.retroFit

import com.easypick.easypick.model.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Gateway {

    @GET("/stores/{id}/categories")
    fun getCategoryByStoreId(@Path("id") id:Long): Call<List<Category>>

}