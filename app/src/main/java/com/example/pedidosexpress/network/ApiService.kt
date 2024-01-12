package com.example.pedidosexpress.views.consumidor

import com.example.pedidosexpress.model.CodigoEntregaRequest
import com.example.pedidosexpress.model.PagosData
import com.example.pedidosexpress.model.Pedido
import com.example.pedidosexpress.model.PedidoAsignado
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/getProducts")
    fun obtenerDatos(): Call<List<Producto>>

    @GET("/obtenerCarito")
    fun obtenerCarito(@Query("username") username: String):Call<List<CarritoData>>

    @GET("/DetallePedidos")
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
    @GET("/ver_pedidos_asignados/{nombre_repartidor}")
    fun verPedidosRepartidor(@Path("nombre_repartidor") nombreRepartidor: String): Call<List<PedidoAsignado>>

    // Nueva solicitud para confirmar un pedido asignado por un repartidor
    @PUT("/confirmar_pedido/{repartidor}")
    fun confirmarEntrega(@Path("repartidor") repartidor: String): Call<Void>

    // Nuevo método para confirmar la entrega de un pedido asignado
    @PUT("/entregar_pedido/{repartidor}")
    fun entregarPedido(@Path("repartidor") repartidor: String, @Body codigoEntrega: CodigoEntregaRequest): Call<Void>
}
