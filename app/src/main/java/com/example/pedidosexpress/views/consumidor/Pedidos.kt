package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.pedidosexpress.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Pedidos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            handleNavigation(
                item
            )
        }
        // Configurar el fragmento de inicio al inicio
        loadFragment(HomeCFragment())
    }

    fun handleNavigation(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == HomeConsumidor.MENU_HOME) {
            startActivity(Intent(this@Pedidos, HomeConsumidor::class.java))
            return true
        }
        if (itemId == HomeConsumidor.MENU_CARRITO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@Pedidos, Carrito::class.java))
            return true
        }
        return if (itemId == HomeConsumidor.MENU_USUARIO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@Pedidos, CuentaConsumidor::class.java))
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