package com.example.pedidosexpress.views.consumidor.homeCosumidor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.CarritoConsumidor.Carrito
import com.example.pedidosexpress.views.consumidor.CuentaConsumidor
import com.example.pedidosexpress.views.repartidor.RepartidorActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeConsumidor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeconsumidor) // Establece el layout o interfaz de la actividad

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MapaCFragment>(R.id.FragmentMapaContainer)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            handleNavigation(
                item
            )
        }
        // Configurar el fragmento de inicio al inicio
        loadFragment(HomeCFragment())
    }

    // Boton de Navegación
    fun handleNavigation(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == MENU_HOME) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@HomeConsumidor, HomeConsumidor::class.java))
            return true
        }
        if (itemId == MENU_CARRITO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@HomeConsumidor, Carrito::class.java))
            return true
        }
        return if (itemId == MENU_USUARIO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@HomeConsumidor, CuentaConsumidor::class.java))
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.home_item) {
            // Acción para el botón "repartidor"
            Toast.makeText(this, "Seleccionaste repartidor", Toast.LENGTH_SHORT).show()
            // Crea un Intent para iniciar RepartidorActivity
            val intent = Intent(this, RepartidorActivity::class.java)
            startActivity(intent)
            true
        } else if (id == R.id.orders_item) {
            // Acción para el botón "Usuuario"
            Toast.makeText(this, "Seleccionaste Usuario", Toast.LENGTH_SHORT).show()
            true
        } else if (id == R.id.user_item) {
            // Acción para el botón "repartidor"
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
        val MENU_CARRITO = R.id.orders_item
        val MENU_USUARIO = R.id.user_item
    }
}