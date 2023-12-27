package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HistorialEntregas : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerRepartidor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historialentregas)

        bottomNavigationHandler = BottomNavigationHandlerRepartidor(this)

        val btnBack: FloatingActionButton = findViewById(R.id.btnback)
        val btnDetalle: Button = findViewById(R.id.btnDetallepedido)

        // Agrega un evento de clic al botón de retroceso
        btnBack.setOnClickListener {
            // Simula el comportamiento del botón de retroceso del sistema
            onBackPressed()
        }

        // Agrega un evento de clic al botón "Detalle de Pedido"
        btnDetalle.setOnClickListener {
            // Llama a la función que muestra el cuadro de diálogo
            mostrarDetallePedidoDialog()
        }
    }

    // Función para mostrar el cuadro de diálogo de detalle del pedido
    private fun mostrarDetallePedidoDialog() {
        // Construye el cuadro de diálogo con MaterialAlertDialogBuilder
        MaterialAlertDialogBuilder(this)
            .setTitle("Detalle del Pedido")
            .setMessage("Aquí puedes mostrar información detallada sobre el pedido.")
            .setPositiveButton("Aceptar") { dialog, which ->
                // Maneja el clic en el botón "Aceptar" si es necesario
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                // Maneja el clic en el botón "Cancelar" si es necesario
            }
            .show() // Muestra el cuadro de diálogo
    }
}
