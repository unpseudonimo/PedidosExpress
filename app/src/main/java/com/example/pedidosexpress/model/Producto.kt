package com.example.pedidosexpress.model

data class Producto(
    val nombre: String,
    val descripcionProducto: String,
    val imagen: Int,  // Recurso de imagen, por ejemplo, R.drawable.placeholder_imagen_producto
    val precio: Double
)
