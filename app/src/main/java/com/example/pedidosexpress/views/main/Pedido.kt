package com.example.pedidosexpress.views.common

import com.example.pedidosexpress.views.consumidor.Producto

data class Pedido(
    val _id: Any?,  // Cambiado de Map<String, String> a Any
    val nombre_cliente: String?,
    val nombre_repartidor: String?,
    val productos: List<Producto>,
    val total: Double,
    val estado: String?
)