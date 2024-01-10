package com.example.pedidosexpress.model

import com.example.pedidosexpress.views.consumidor.Producto

data class Pedido(
    val _id: Any?,
    val productos: List<Producto>,
    val total: Double,
    val estado: String?
)