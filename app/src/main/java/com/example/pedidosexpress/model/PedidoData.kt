package com.example.pedidosexpress.model

data class PedidoData(
    val direccion_entrega: String,
    val productos: List<ProductoData>,
    val total: Double
)

data class PedidoAsignado(
    val direccion: String,
    val productos: List<ProductoDataAsignado>,
    val total: Double
)

data class ProductoDataAsignado(
    val Cantidad_producto: Int,
    val id_producto: Int,
    val nombre_producto: String,
    val precio_producto: Double
)
