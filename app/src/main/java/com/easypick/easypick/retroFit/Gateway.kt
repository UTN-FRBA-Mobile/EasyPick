package com.easypick.easypick.retroFit

import com.easypick.easypick.model.Category
import com.easypick.easypick.model.Catalogo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Gateway {

    @GET("stores/{id}/Categories/categories")
    fun getCatalogoByStoreId(@Path("id") id:Long): Call<List<Catalogo>>

    @GET("/categories/{id}")
    fun getProductByCategoryId(@Path("id") id:Int): Call<List<Catalogo>>

}