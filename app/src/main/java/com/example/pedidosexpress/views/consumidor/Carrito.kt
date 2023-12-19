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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val listViewCarrito: ListView = findViewById(R.id.listViewCarrito)
        val btnpedidos = findViewById<Button>(R.id.btnpedidos)

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            handleNavigation(
                item
            )
        }
        // Configurar el fragmento de inicio al inicio
        loadFragment(HomeCFragment())

        val productosEnCarrito = mutableListOf(
            Producto("Jabon Zest", "Limpieza refrescante para tu piel", R.drawable.placeholder_imagen_producto, 22.00),
            // Agrega más productos si es necesario...
        )

        val carritoAdapter = CarritoAdapter(this, productosEnCarrito)
        listViewCarrito.adapter = carritoAdapter

        btnpedidos.setOnClickListener {
            startActivity(Intent(this@Carrito, Pedidos::class.java))
        }
    }
    fun handleNavigation(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == HomeConsumidor.MENU_HOME) {
            startActivity(Intent(this@Carrito, HomeConsumidor::class.java))
            return true
        }
        if (itemId == HomeConsumidor.MENU_CARRITO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@Carrito, Carrito::class.java))
            return true
        }
        return if (itemId == HomeConsumidor.MENU_USUARIO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@Carrito, CuentaConsumidor::class.java))
            true
        } else {
            // Agrega más casos según sea necesario para otros ítems del menú
            false
        }
    }

    private fun loadFragment(fragment: HomeCFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.home_item, fragment)
            .commit()
    }

    companion object {
        val MENU_HOME = R.id.home_item
        val MENU_CARRITO = R.id.orders_item
        val MENU_USUARIO = R.id.user_item
    }
}
