package com.example.pedidosexpress.views.consumidor

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R

class soporteConsumidorActivity : AppCompatActivity() {

    private lateinit var btnContactarSoporte: Button
    private lateinit var tvMensajeSoporte: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soporte_consumidor)

        // Inicializar vistas
        btnContactarSoporte = findViewById(R.id.btnContactarSoporte)
        tvMensajeSoporte = findViewById(R.id.tvMensajeSoporte)

        // Configurar el evento de clic del botón
        btnContactarSoporte.setOnClickListener {
            contactarSoporte()
        }
    }

    private fun contactarSoporte() {
        // Aquí puedes agregar lógica para contactar al soporte
        // Por ejemplo, cambiar el texto del TextView
        tvMensajeSoporte.text = "¡Soporte contactado!"
    }
}
