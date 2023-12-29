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

    data class Producto(val nombre: String, var esFavorito: Boolean)

    private val listaProductos = mutableListOf(
        Producto("Producto 1", false),
        Producto("Producto 2", false)
        // Agrega más productos según sea necesario
    )

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
    private fun toggleFavorito(posicion: Int) {
        // Cambia el estado de favorito del producto en la lista
        listaProductos[posicion].esFavorito = !listaProductos[posicion].esFavorito
    }
}
