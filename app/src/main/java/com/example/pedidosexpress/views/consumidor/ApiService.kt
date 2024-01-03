package com.example.pedidosexpress.views.consumidor

import com.example.pedidosexpress.views.main.Pedido
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/all_products")
    fun obtenerDatos(): Call<List<Producto>>

    @GET("/obtenerCarito")
    fun obtenerCarito(@Query("username") username: String): Call<List<CarritoData>>

    @POST("/procesarCompra")
    fun procesarCompra(@Body productos: List<CarritoData>): Call<Void>

    // Agregado el método para obtener el historial de entregas
    @GET("/obtenerHistorialEntregas")
    fun obtenerHistorialEntregas(): Call<List<Pedido>>

    // Nuevo método para obtener el ID del repartidor actual
    @GET("/ver_pedidos_repartidor/{nombre_repartidor}")
    fun verPedidosRepartidor(@Path("nombre_repartidor") nombreRepartidor: String): Call<List<Pedido>>

}
