package com.example.pedidosexpress.views.repartidor
import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import com.example.pedidosexpress.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationHandlerRepartidor(private val activity: Activity) {

    private val bottomNavigationView: BottomNavigationView =
        activity.findViewById(R.id.bottom_navigation)

    init {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            handleNavigation(item)
        }
    }

    private fun handleNavigation(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.inicio_item -> {
                startActivity(HomeRepartidor::class.java)
                return true
            }
            R.id.entregas_item -> {
                startActivity(HistorialEntregas::class.java)
                return true
            }
            R.id.cuenta_item -> {
                startActivity(CuentaRepartidor::class.java)
                return true
            }
        }
        return false
    }

    private fun startActivity(cls: Class<*>) {
        activity.startActivity(Intent(activity, cls))
    }
}
