package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.BottomNavigationHandlerConsumidor
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HistorialEntregas : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerRepartidor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historialentregas)

        bottomNavigationHandler = BottomNavigationHandlerRepartidor(this)
        val btnBack: FloatingActionButton = findViewById<FloatingActionButton>(R.id.btnback)

        // Agrega un evento de clic al botón de retroceso
        btnBack.setOnClickListener {
            // Simula el comportamiento del botón de retroceso del sistema
            onBackPressed()
        }

    }
}