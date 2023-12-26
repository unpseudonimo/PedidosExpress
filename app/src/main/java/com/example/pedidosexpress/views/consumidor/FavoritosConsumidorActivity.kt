package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.google.android.material.bottomnavigation.BottomNavigationView


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

        /*
                val ivFavorito1: ImageView = findViewById(R.id.ivFavorito1)
                val ivFavorito2: ImageView = findViewById(R.id.ivFavorito2)
        */
/*
        // Escucha el clic en la imagen de favorito y actualiza la lista y la interfaz
        ivFavorito1.setOnClickListener {
            // Supongamos que el primer producto está en la posición 0
            toggleFavorito(0)
            actualizarInterfaz()
        }

        ivFavorito2.setOnClickListener {
            // Supongamos que el segundo producto está en la posición 1
            toggleFavorito(1)
            actualizarInterfaz()
        }

        // Actualiza la interfaz inicialmente
        actualizarInterfaz()*/
    }


    private fun toggleFavorito(posicion: Int) {
        // Cambia el estado de favorito del producto en la lista
        listaProductos[posicion].esFavorito = !listaProductos[posicion].esFavorito
    }

    /*
    private fun actualizarInterfaz() {
        // Actualiza la interfaz según el estado de favorito de cada producto
        val ivFavorito1: ImageView = findViewById(R.id.ivFavorito1)
        val ivFavorito2: ImageView = findViewById(R.id.ivFavorito2)

        // Supongamos que el primer producto está en la posición 0
        if (listaProductos[0].esFavorito) {
            ivFavorito1.setImageResource(R.drawable.ic_favorito)
        } else {
            ivFavorito1.setImageResource(R.drawable.ic_no_favorito)
        }

        // Supongamos que el segundo producto está en la posición 1
        if (listaProductos[1].esFavorito) {
            ivFavorito2.setImageResource(R.drawable.ic_favorito)
        } else {
            ivFavorito2.setImageResource(R.drawable.ic_no_favorito)
        }

        // Puedes repetir el patrón para más productos según sea necesario
    }*/
}
