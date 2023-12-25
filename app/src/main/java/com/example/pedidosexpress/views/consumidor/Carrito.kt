package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.model.Producto
import com.google.android.material.bottomnavigation.BottomNavigationView

class Carrito : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerConsumidor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        bottomNavigationHandler = BottomNavigationHandlerConsumidor(this)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val listViewCarrito: ListView = findViewById(R.id.listViewCarrito)
        val btnpedidos = findViewById<Button>(R.id.btnpedidos)


        btnpedidos.setOnClickListener {
            startActivity(Intent(this@Carrito, Pedidos::class.java))
        }
    }
}
