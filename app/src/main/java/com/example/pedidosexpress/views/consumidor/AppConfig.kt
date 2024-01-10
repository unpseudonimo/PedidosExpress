package com.example.pedidosexpress.views.consumidor

import com.example.pedidosexpress.core.Constantes

class AppConfig private constructor() {
    companion object {
        // URL base del servidor
        private const val BASE_URL = "http://192.168.1.70:5000"

        // Método para obtener la URL completa de una ruta específica en el servidor
        fun buildApiUrl(path: String): String {
            return "$BASE_URL/$path"
        }
    }
}