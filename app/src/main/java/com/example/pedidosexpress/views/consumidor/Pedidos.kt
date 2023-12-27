package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import com.example.pedidosexpress.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Pedidos : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        val btnBack: FloatingActionButton = findViewById<FloatingActionButton>(R.id.btnback)

        // Agrega un evento de clic al botón de retroceso
        btnBack.setOnClickListener {
            // Simula el comportamiento del botón de retroceso del sistema
            onBackPressed()
        }
    }

}