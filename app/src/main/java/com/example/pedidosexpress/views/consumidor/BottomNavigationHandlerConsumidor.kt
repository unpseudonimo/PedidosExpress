package com.example.pedidosexpress.views.consumidor

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.Login
import com.example.pedidosexpress.views.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationHandlerConsumidor(private val activity: Activity) {
    private var username: String =Login.getUsernameFromSharedPreferences(activity.applicationContext)

    private val bottomNavigationView: BottomNavigationView =
        activity.findViewById(R.id.bottom_navigation)

    private val sharedPreferences =
        activity.getSharedPreferences("bottom_nav", Activity.MODE_PRIVATE)

    init {
        // Recupera el ítem seleccionado guardado previamente
        val selectedItemId = sharedPreferences.getInt("selected_item_id", -1)
        if (selectedItemId == -1) {
            // Si no hay un ítem seleccionado guardado, establece el valor predeterminado en inicio_item
            sharedPreferences.edit().putInt("selected_item_id", R.id.inicio_item).apply()
            bottomNavigationView.selectedItemId = R.id.inicio_item
        } else {
            // Si hay un ítem seleccionado guardado, úsalo
            bottomNavigationView.selectedItemId = selectedItemId
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            handleNavigation(item)
        }
    }
    fun onBackPressed(): Boolean {
        showExitConfirmationDialog()
        // Indica que el evento de retroceso ha sido manejado
        return true
    }

    private fun showExitConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle("Salir")
        alertDialogBuilder.setMessage("¿Estás seguro de que quieres salir?")
        alertDialogBuilder.setCancelable(true)

        alertDialogBuilder.setPositiveButton("Aceptar") { dialog, _ ->
            // Si se hace clic en Aceptar, finaliza la actividad
            dialog.dismiss()
            clearSelectedItem()
            // Cerrar la aplicación
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)
        }

        alertDialogBuilder.setNegativeButton("Cancelar") { dialog, _ ->
            // Si se hace clic en Cancelar, simplemente cierra el diálogo
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun handleNavigation(item: MenuItem): Boolean {
        val itemId = item.itemId
        sharedPreferences.edit().putInt("selected_item_id", itemId).apply()
        when (itemId) {
            R.id.inicio_item -> {
                // Lógica específica para el ítem de inicio, si es necesario
                val intent = Intent(activity, HomeConsumidor::class.java)
                activity.finish()
                startActivity(intent)
            }
            R.id.carrito_item -> {
                val intent = Intent(activity, Carrito::class.java)
                activity.finish()
                startActivity(intent)
            }
            R.id.cuenta_item -> {
                // Lógica específica para el ítem de cuenta
                val username = Login.getUsernameFromSharedPreferences(activity.applicationContext)
                Log.d("Username", "Valor de username: $username")
                if (username.isEmpty()) {
                    // Si userId está vacío, muestra un mensaje para iniciar sesión
                    Toast.makeText(activity.applicationContext, "Inicia sesión para agregar productos al carrito", Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, MainActivity::class.java)
                    activity.finish()
                    startActivity(intent)
                } else {
                    // Usuario logeado, permite acceder a la actividad CuentaConsumidor
                    val intent = Intent(activity, CuentaConsumidor::class.java)
                    activity.finish()
                    startActivity(intent)
                }
                return true
            }
        }
        return false
    }

    companion object {
        private var instance: BottomNavigationHandlerConsumidor? = null

        fun getInstance(activity: Activity): BottomNavigationHandlerConsumidor {
            if (instance == null) {
                instance = BottomNavigationHandlerConsumidor(activity)
            }
            return instance!!
        }

        fun clearSelectedItem() {
            // Limpia el valor del ítem seleccionado al cerrar la aplicación
            instance?.sharedPreferences?.edit()?.remove("selected_item_id")?.apply()
        }
    }

    private fun startActivity(intent: Intent) {
        activity.startActivity(intent)
    }

}