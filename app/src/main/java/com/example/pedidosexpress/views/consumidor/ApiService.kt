package com.example.pedidosexpress.views.consumidor

import retrofit2.Call
import retrofit2.http.GET
interface ApiService {
    @GET("/all_products")
    fun obtenerDatos(): Call<List<Producto>>
}