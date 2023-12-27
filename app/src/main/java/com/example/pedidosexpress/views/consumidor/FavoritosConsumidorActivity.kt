package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavoritosConsumidorActivity : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerConsumidor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos_consumidor)


        bottomNavigationHandler = BottomNavigationHandlerConsumidor(this)
        val btnBack: FloatingActionButton = findViewById<FloatingActionButton>(R.id.btnback)

        // Agrega un evento de clic al botón de retroceso
        btnBack.setOnClickListener {
            // Simula el comportamiento del botón de retroceso del sistema
            onBackPressed()
        }
    }
}
