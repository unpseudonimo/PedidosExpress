package com.example.pedidosexpress.views.consumidor

import com.google.gson.annotations.SerializedName
data class RespuestaApi(
    @SerializedName("productos") val productos: List<Producto>, // Ajusta según la respuesta real de tu API
    @SerializedName("productosCarrito") val productosCarrito: List<CarritoData> // Nueva propiedad para los productos en el carrito
)


