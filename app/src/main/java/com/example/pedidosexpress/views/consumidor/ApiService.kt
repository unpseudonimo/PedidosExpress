package com.example.pedidosexpress.views.consumidor

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("/all_products")
    fun obtenerDatos(): Call<List<Producto>>

    @GET("/obtenerCarito")
    fun obtenerCarito(@Query("username") username: String):Call<List<CarritoData>>

    @POST("/procesarCompra")
    fun procesarCompra(@Body productos: List<CarritoData>): Call<Void>


}