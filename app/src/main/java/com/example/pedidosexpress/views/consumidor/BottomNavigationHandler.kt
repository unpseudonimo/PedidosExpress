package com.example.pedidosexpress.views.consumidor
import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import com.example.pedidosexpress.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationHandler(private val activity: Activity) {

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
            R.id.home_item -> {
                startActivity(HomeConsumidor::class.java)
                return true
            }
            R.id.orders_item -> {
                startActivity(Pedidos::class.java)
                return true
            }
            R.id.user_item -> {
                startActivity(CuentaConsumidor::class.java)
                return true
            }
        }
        return false
    }

    private fun startActivity(cls: Class<*>) {
        activity.startActivity(Intent(activity, cls))
    }
}
