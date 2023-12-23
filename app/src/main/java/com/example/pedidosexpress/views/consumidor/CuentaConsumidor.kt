package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView



class CuentaConsumidor : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuenta_consumidor) // Establece el layout de la actividad

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val btnfavoritos = findViewById<Button>(R.id.btnFavoritos)
        val txtcerrarsesion = findViewById<TextView>(R.id.logout)
        val btnhistpedidos = findViewById<Button>(R.id.btnHistorial)

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            handleNavigation(
                item
            )
        }
        // Configurar el fragmento de inicio al inicio
        loadFragment(HomeCFragment())

        btnfavoritos.setOnClickListener {
            startActivity(Intent(this@CuentaConsumidor, FavoritosConsumidorActivity::class.java))

        }

        txtcerrarsesion.setOnClickListener {
            startActivity(Intent(this@CuentaConsumidor, MainActivity::class.java))
        }

        btnhistpedidos.setOnClickListener {
            startActivity(Intent(this@CuentaConsumidor, Pedidos::class.java))
        }

    }

    fun handleNavigation(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == HomeConsumidor.MENU_HOME) {
            startActivity(Intent(this@CuentaConsumidor, HomeConsumidor::class.java))
            return true
        }
        if (itemId == HomeConsumidor.MENU_CARRITO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@CuentaConsumidor, Carrito::class.java))
            return true
        }
        return if (itemId == HomeConsumidor.MENU_USUARIO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@CuentaConsumidor, CuentaConsumidor::class.java))
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