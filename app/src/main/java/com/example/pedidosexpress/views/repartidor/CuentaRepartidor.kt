package com.example.pedidosexpress.views.repartidor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.BottomNavigationHandlerConsumidor
import com.example.pedidosexpress.views.consumidor.FavoritosConsumidorActivity
import com.example.pedidosexpress.views.consumidor.Pedidos
import com.example.pedidosexpress.views.main.MainActivity

class CuentaRepartidor : AppCompatActivity() {
    private lateinit var btnIniciarSesionConsumidor: Button

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerRepartidor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuenta_repartidor) // Establece el layout de la actividad
        // Inicializa el botón después de setContentView

        bottomNavigationHandler = BottomNavigationHandlerRepartidor(this)

        val txtcerrarsesion = findViewById<TextView>(R.id.logout)

        txtcerrarsesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
}