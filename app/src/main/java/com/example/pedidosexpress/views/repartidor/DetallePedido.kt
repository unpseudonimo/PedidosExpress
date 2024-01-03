package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R

class DetallePedido : AppCompatActivity() {
    // ... (código existente)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ... (código existente)

        // Obtener datos del Intent
        val intent = intent
        if (intent != null && intent.hasExtra("pedido_id")) {
            val pedidoId = intent.getStringExtra("pedido_id")

            // Aquí puedes usar el ID del pedido para cargar los detalles del pedido desde tu fuente de datos
            cargarDetallesPedido(pedidoId)
        } else {
            // Manejar el caso en que no se proporciona el ID del pedido
            // ...
            finish() // Cerrar la actividad si no hay datos válidos
        }
    }

    private fun cargarDetallesPedido(pedidoId: String?) {
        // Aquí puedes implementar la lógica para cargar los detalles del pedido
        // Puedes obtener información del pedido de tu fuente de datos (base de datos, API, etc.)
        // y luego actualizar tus elementos visuales en consecuencia.

        // Ejemplo: Mostrar el ID del pedido en el TextView
    }
}

