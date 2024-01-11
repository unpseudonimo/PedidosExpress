package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R

class HistorialEntregas : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerRepartidor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historialentregas) // Establece el layout de la actividad
        // Inicializa el botón después de setContentView

        bottomNavigationHandler = BottomNavigationHandlerRepartidor(this)

    }


}
