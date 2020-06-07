package com.easypick.easypick.interfaces

import com.easypick.easypick.model.Order
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface MercadoPagoService {
    @POST("/checkout/preferences")
    @Headers("Content-Type: application/json")
    fun createPreferences(@Body body: Order, @Query("access_token") access_token: String):
            Call<ResponseBody>
}