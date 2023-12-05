package com.example.pedidosexpress

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.views.consumidor.FeedConsumidor
import com.example.pedidosexpress.views.home.HomeFragment
import com.example.pedidosexpress.views.tienda.RepartidorActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home) // Establece el layout o interfaz de la actividad
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            handleNavigation(
                item
            )
        }
        // Configurar el fragmento de inicio al inicio
        loadFragment(HomeFragment())
    }

    // Boton de Navegación
    fun handleNavigation(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == MENU_HOME) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@Home, Home::class.java))
            return true
        }
        if (itemId == MENU_CARRITO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@Home, Carrito::class.java))
            return true
        }
        return if (itemId == MENU_USUARIO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@Home, FeedConsumidor::class.java))
            true
        } else {
            // Agrega más casos según sea necesario para otros ítems del menú
            false
        }
    }

    private fun loadFragment(fragment: HomeFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.home_item, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.home_item) {
            // Acción para el botón "Repartidor"
            Toast.makeText(this, "Seleccionaste Repartidor", Toast.LENGTH_SHORT).show()
            // Crea un Intent para iniciar RepartidorActivity
            val intent = Intent(this, RepartidorActivity::class.java)
            startActivity(intent)
            true
        } else if (id == R.id.car_item) {
            // Acción para el botón "Usuuario"
            Toast.makeText(this, "Seleccionaste Usuario", Toast.LENGTH_SHORT).show()
            true
        } else if (id == R.id.user_item) {
            // Acción para el botón "Repartidor"
            Toast.makeText(this, "Saliendo...", Toast.LENGTH_SHORT).show()
            // Acción para el botón "Salir"
            finish() // Cierra la actividad
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val MENU_HOME = R.id.home_item
        val MENU_CARRITO = R.id.car_item
        val MENU_USUARIO = R.id.user_item
    }
}