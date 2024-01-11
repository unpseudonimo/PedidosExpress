package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetallePedido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)

        // Obtener datos del Intent
        val intent = intent
        if (intent != null) {
            val direccion = intent.getStringExtra("Direccion") ?: "Dirección no disponible"
            val total = intent.getDoubleExtra("Total", 0.0)
            val productos = intent.getStringExtra("Productos") ?: ""

            // Mostrar los detalles en los elementos visuales
            mostrarDetallesPedido(direccion, total, productos)
        } else {
            // Manejar el caso en que no se proporcionan datos válidos
            finish()
        }

        // Configurar el botón de retroceso
        val btnBack: FloatingActionButton = findViewById(R.id.btnback)
        btnBack.setOnClickListener {
            onBackPressed() // Volver a la actividad anterior al hacer clic en el botón de retroceso
        }
    }

    private fun mostrarDetallesPedido(direccion: String, total: Double, productos: String) {
        val tvDetallePedido: TextView = findViewById(R.id.tvDetallePedido)
        val tvDireccion: TextView = findViewById(R.id.tvDireccion)
        val tvTotal: TextView = findViewById(R.id.tvTotal)
        val tvProductos: TextView = findViewById(R.id.tvProductos)

        // Mostrar los detalles en los TextViews
        tvDetallePedido.text = "Detalles del Pedido"
        tvDireccion.text = "Dirección: $direccion"
        tvTotal.text = "Total: $total"
        tvProductos.text = "Productos:\n$productos"
    }
}
