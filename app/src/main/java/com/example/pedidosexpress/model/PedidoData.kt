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

// Clases de datos Kotlin para reflejar la estructura del JSON
data class HistorialPedidosResponse(val pedidos: List<Pedido>)

data class PedidoEntregado(
    val Username: String,
    val direccion: String,
    val telefono: String,
    val productos: List<ProductoDataEntregado>,
    val total: Double,
    val estado_pedido: String,
    val estado_repartidor: String,
    val nombre_repartidor: String
)

data class ProductoDataEntregado(
    val id_producto: Int,
    val nombre_producto: String,
    val precio_producto: Double,
    val Cantidad_producto: Int,
    val imagen_producto: String
)