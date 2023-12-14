package com.example.pedidosexpress.views.consumidor

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R

class notificacionConsumidorActivity : AppCompatActivity() {

    private lateinit var btnMostrarNotificacion: Button
    private lateinit var tvMensajeNotificacion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificacion_consumidor)

        // Inicializar vistas
        btnMostrarNotificacion = findViewById(R.id.btnMostrarNotificacion)
        tvMensajeNotificacion = findViewById(R.id.tvMensajeNotificacion)

        // Configurar el evento de clic del botón
        btnMostrarNotificacion.setOnClickListener {
            mostrarNotificacion()
        }
    }

    private fun mostrarNotificacion() {
        // Aquí puedes agregar lógica para mostrar una notificación
        // Por ejemplo, cambiar el texto del TextView
        tvMensajeNotificacion.text = "¡Notificación mostrada!"
    }
}
