package com.example.pedidosexpress.views.consumidor

import com.example.pedidosexpress.views.common.Pedido
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
    fun obtenerCarito(@Query("username") username: String):Call<List<CarritoData>>

    @POST("/compraDatos")
    fun procesarCompra(@Query("username") username: String):Call<List<PagosData>>

    @GET("/search_products")
    fun buscarProducto(@Query("nombre") nombreProducto: String): Call<List<Producto>>

    @POST("/recommend")
    fun getRecommendations(@Body selectedP: ProductoRequest): Call<List<Producto>>

    @GET("/favoritos")
    fun obtenerFavorito(@Query("username") username: String): Call<List<Producto>>

    // Agregado el método para obtener el historial de entregas
    @GET("/obtenerHistorialEntregas")
    fun obtenerHistorialEntregas(): Call<List<Pedido>>

    // Nuevo método para obtener el ID del repartidor actual
    @GET("/ver_pedidos_repartidor/{nombre_repartidor}")
    fun verPedidosRepartidor(@Path("nombre_repartidor") nombreRepartidor: String): Call<List<Pedido>>

}
