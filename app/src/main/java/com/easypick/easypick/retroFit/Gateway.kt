package com.easypick.easypick.retroFit

import com.easypick.easypick.model.Category
import com.easypick.easypick.model.Producto
import com.easypick.easypick.model.Remarked
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


interface Gateway {
    @GET("api.php")
    fun getProductoByCategoryId(@Query("store") store: Long,
                                @Query("categoria") categoria: Int): Call<List<Producto>>

    @GET("/stores/{id}/categories")
    fun getCategoryByStoreId(@Path("id") id:Long): Call<List<Category>>

    @GET("/stores/{id}/remarkeds")
    fun getRemarkedsByStoreId(@Path("id") id:Long): Call<List<Remarked>>
}