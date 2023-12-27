package com.example.pedidosexpress.views.consumidor
import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import com.example.pedidosexpress.R
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
                startActivity(getDestinationActivity(itemId))
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

    private fun startActivity(cls: Class<*>) {
        activity.startActivity(Intent(activity, cls))
    }
}
