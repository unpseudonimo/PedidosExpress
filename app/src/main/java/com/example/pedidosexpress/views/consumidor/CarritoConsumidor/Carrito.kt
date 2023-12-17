package com.example.pedidosexpress.views.consumidor.CarritoConsumidor

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.model.Producto
import com.example.pedidosexpress.views.consumidor.BottomNavigationHandler

class Carrito : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        bottomNavigationHandler = BottomNavigationHandler(this)

        val listViewCarrito: ListView = findViewById(R.id.listViewCarrito)
        val productosEnCarrito = mutableListOf(
            Producto("Jabon Zest", "Limpieza refrescante para tu piel", R.drawable.placeholder_imagen_producto, 22.00),
            // Agrega más productos si es necesario...
        )

        val carritoAdapter = CarritoAdapter(this, productosEnCarrito)
        listViewCarrito.adapter = carritoAdapter
    }
}
