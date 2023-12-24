package com.example.pedidosexpress.views.consumidor

import com.google.gson.annotations.SerializedName
data class RespuestaApi(
    @SerializedName("productos") val productos: List<Producto>, // Ajusta según la respuesta real de tu API
    @SerializedName("productosEnCarrito") val productosEnCarrito: List<ProductoEnCarrito> // Nueva propiedad para los productos en el carrito
)

data class ProductoEnCarrito(
    @SerializedName("id_producto") val idProducto: Int,
    @SerializedName("cantidad") val cantidad: Int
)
