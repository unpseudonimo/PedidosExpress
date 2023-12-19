package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R

class CuentaConsumidor : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuenta_consumidor) // Establece el layout de la actividad
        val btnmapa = findViewById<TextView>(R.id.btnmapa)

        bottomNavigationHandler = BottomNavigationHandler(this)


    }
}