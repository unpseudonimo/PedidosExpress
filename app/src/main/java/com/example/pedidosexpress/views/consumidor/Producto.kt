package com.example.pedidosexpress.views.consumidor

import com.google.gson.annotations.SerializedName
data class Producto(
    @SerializedName("id_producto") val idProducto: Int,
    @SerializedName("nombre_producto") val nombreProducto: String,
    @SerializedName("descripcion_producto") val descripcionProducto: String,
    @SerializedName("imagen_producto") val imagenProducto: String,
    @SerializedName("precio_producto") val precioProducto: Double,
    @SerializedName("Cantidad_producto") var cantidadEnCarrito: Int,
    @SerializedName("Username") var nombreUsuario: String,
    var esRecomendacion: Boolean = false,
    var esFavorito: Boolean = false,
)





