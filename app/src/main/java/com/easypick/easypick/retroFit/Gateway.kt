package com.easypick.easypick.retroFit

import com.easypick.easypick.model.Category
import com.squareup.okhttp.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface Gateway {

    @GET("/pepe")
    fun getCategoryById(@Url id:Long): Category

}