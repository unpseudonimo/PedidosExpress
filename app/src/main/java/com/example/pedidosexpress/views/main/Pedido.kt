package com.example.pedidosexpress.views.main

import com.example.pedidosexpress.views.consumidor.Producto

data class Pedido(
    val _id: Id,
    val nombre_cliente: String,
    val nombre_repartidor: String,
    val productos: List<Producto>,
    val total: Double,
    val estado: String
)

data class Id(
    val `$oid`: String
)
