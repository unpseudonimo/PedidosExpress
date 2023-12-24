package com.example.pedidosexpress.views.consumidor

import com.google.gson.annotations.SerializedName
data class Producto(
    @SerializedName("id_producto") val idProducto: Int,
    @SerializedName("nombre_producto") val nombreProducto: String,
    @SerializedName("descripcion_producto") val descripcionProducto: String,
    @SerializedName("imagen_producto") val imagenProducto: String,
    @SerializedName("precio_producto") val precioProducto: Double,
    var cantidadEnCarrito: Int = 0  //
)