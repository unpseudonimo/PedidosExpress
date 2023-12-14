package com.example.pedidosexpress.views.consumidor

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R

class recomendacionConsumidorActivity : AppCompatActivity() {

    private lateinit var btnEnviarRecomendacion: Button
    private lateinit var tvMensajeRecomendacion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendacion_consumidor)

        // Inicializar vistas
        btnEnviarRecomendacion = findViewById(R.id.btnEnviarRecomendacion)
        tvMensajeRecomendacion = findViewById(R.id.tvMensajeRecomendacion)

        // Configurar el evento de clic del botón
        btnEnviarRecomendacion.setOnClickListener {
            enviarRecomendacion()
        }
    }

    private fun enviarRecomendacion() {
        // Aquí puedes agregar lógica para enviar una recomendación
        // Por ejemplo, cambiar el texto del TextView
        tvMensajeRecomendacion.text = "¡Recomendación enviada!"
    }
}
