package com.example.pedidosexpress.views.consumidor

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.MainActivity
import com.example.pedidosexpress.views.main.login
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationHandlerConsumidor(private val activity: Activity) {

    private val bottomNavigationView: BottomNavigationView =
        activity.findViewById(R.id.bottom_navigation)

    private val sharedPreferences =
        activity.getSharedPreferences("bottom_nav", Activity.MODE_PRIVATE)

    init {
        // Recupera el ítem seleccionado guardado previamente
        val selectedItemId = sharedPreferences.getInt("selected_item_id", R.id.inicio_item)
        bottomNavigationView.selectedItemId = selectedItemId

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            handleNavigation(item)
        }
    }

    private fun handleNavigation(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.inicio_item, R.id.carrito_item, R.id.cuenta_item -> {
                // Guarda el ítem seleccionado actual
                sharedPreferences.edit().putInt("selected_item_id", itemId).apply()
                val intent = Intent(activity, getDestinationActivity(itemId))
                startActivity(intent)
                return true
            }
            R.id.cuenta_item -> {
                // Lógica específica para el ítem de usuario
                val username = login.getUsernameFromSharedPreferences(activity.applicationContext)
                if (username.isEmpty()) {
                    // Si el userId está vacío, muestra un mensaje para iniciar sesión
                    // Puedes redirigir a la pantalla de inicio de sesión aquí
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // Usuario logeado, permite acceder a la actividad CuentaConsumidor
                    val intent = Intent(activity, CuentaConsumidor::class.java)
                    startActivity(intent)
                }
                return true
            }
        }
        return false
    }


    private fun getDestinationActivity(itemId: Int): Class<*> {
        return when (itemId) {
            R.id.inicio_item -> HomeConsumidor::class.java
            R.id.carrito_item -> Carrito::class.java
            R.id.cuenta_item -> CuentaConsumidor::class.java
            else -> HomeConsumidor::class.java
        }
    }

    private fun startActivity(intent: Intent) {
        activity.startActivity(intent)
    }

}
