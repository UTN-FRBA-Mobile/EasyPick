package com.easypick.easypick.retroFit

import com.easypick.easypick.model.Category
import com.easypick.easypick.model.Producto
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


interface Gateway {
    @GET("api.php")
    fun getProductoByCategoryId(@Query("store") store: Long,
                                @Query("categoria") categoria: Long): Call<List<Producto>>

    @GET("/stores/{id}/categories")
    fun getCategoryByStoreId(@Path("id") id:Long): Call<List<Category>>
}