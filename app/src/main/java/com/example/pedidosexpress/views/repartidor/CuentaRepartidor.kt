package com.example.pedidosexpress.views.repartidor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.HomeConsumidor
import com.example.pedidosexpress.views.main.Login
import com.example.pedidosexpress.views.main.MainActivity

class CuentaRepartidor : AppCompatActivity() {
    private lateinit var btnIniciarSesionConsumidor: Button
    private lateinit var bottomNavigationHandler: BottomNavigationHandlerRepartidor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuenta_repartidor)

        bottomNavigationHandler = BottomNavigationHandlerRepartidor(this)

        val txtcerrarsesion = findViewById<TextView>(R.id.logout)

        txtcerrarsesion.setOnClickListener {
            // Limpiar las preferencias compartidas al cerrar sesión
            clearUserPreferences()

            // Redirigir a la pantalla de inicio de sesión (MainActivity)
            startActivity(Intent(this, MainActivity::class.java))

            // Finalizar la actividad actual
            finish()
        }
    }

    private fun clearUserPreferences() {
        // Obtener el objeto SharedPreferences
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

        // Editar SharedPreferences para limpiar las preferencias
        val editor = sharedPreferences.edit()
        editor.remove(Login.PREFS_USER_ID_KEY)
        editor.remove(Login.PREFS_USER_ROLE_KEY)
        editor.apply()
    }

}
