package com.example.pedidosexpress.model

import com.google.gson.annotations.SerializedName

data class ProductoPagado(
    @SerializedName("id_producto") val idProducto: Int,
    @SerializedName("nombre_producto") val nombreProducto: String?,
    @SerializedName("precio_producto") val precioProducto: Double,
    @SerializedName("imagen_producto") val imagenProducto: String?,
    @SerializedName("Cantidad_producto") val cantidadProducto: Int
)

data class PagosData(
    @SerializedName("Username") val username: String?,
    @SerializedName("ProductoPagado") val productoPagado: List<ProductoPagado>?,
    @SerializedName("TotalCompra") val totalCompra: Double,
    @SerializedName("EstadoPedido") val estadoPedido: String?,
    @SerializedName("EstadoRepartidor") val estadoRepartidor: String?,
    @SerializedName("Direccion") val direccion: String?,
    @SerializedName("Telefono") val telefono: String?
)