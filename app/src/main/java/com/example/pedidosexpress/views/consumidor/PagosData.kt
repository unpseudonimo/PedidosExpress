package com.example.pedidosexpress.views.consumidor

import com.google.gson.annotations.SerializedName

data class PagosData(
    @SerializedName("id_producto") val idProducto: Int,
    @SerializedName("nombre_producto") val nombreProducto: String,
    @SerializedName("precio_producto") val precioProducto: Double,
    @SerializedName("imagen_producto") val imagenProducto: String
)
