package com.example.pedidosexpress.model

import com.example.pedidosexpress.model.CarritoData
import com.example.pedidosexpress.model.ProductoData
import com.google.gson.annotations.SerializedName
data class RespuestaApi(
    @SerializedName("productoData") val productoData: List<ProductoData>, // Ajusta según la respuesta real de tu API
    @SerializedName("productosCarrito") val productosCarrito: List<CarritoData> // Nueva propiedad para los productoData en el carrito
)


