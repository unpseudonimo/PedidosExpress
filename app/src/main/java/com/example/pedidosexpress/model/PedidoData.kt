package com.example.pedidosexpress.model

data class PedidoAsignado(
    val direccion: String,
    val productos: List<ProductoDataAsignado>,
    val total: Double,
    val estado_pedido: String
)

data class ProductoDataAsignado(
    val Cantidad_producto: Int,
    val id_producto: Int,
    val nombre_producto: String,
    val precio_producto: Double
)
