package com.example.pedidosexpress.funciones

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.HomeConsumidor
import com.example.pedidosexpress.views.repartidor.CuentaRepartidor
import com.example.pedidosexpress.views.repartidor.HistorialEntregas
import com.example.pedidosexpress.views.repartidor.HomeRepartidor
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationHandlerRepartidor(private val activity: Activity) {

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
            R.id.inicio_item, R.id.entregas_item, R.id.cuenta_item -> {
                // Guarda el ítem seleccionado actual
                sharedPreferences.edit().putInt("selected_item_id", itemId).apply()
                startActivity(getDestinationActivity(itemId))
                return true
            }
        }
        return false
    }

    private fun getDestinationActivity(itemId: Int): Class<*> {
        return when (itemId) {
            R.id.inicio_item -> HomeRepartidor::class.java
            R.id.entregas_item -> HistorialEntregas::class.java
            R.id.cuenta_item -> CuentaRepartidor::class.java
            else -> HomeConsumidor::class.java
        }
    }

    private fun startActivity(cls: Class<*>) {
        activity.startActivity(Intent(activity, cls))
    }
}
