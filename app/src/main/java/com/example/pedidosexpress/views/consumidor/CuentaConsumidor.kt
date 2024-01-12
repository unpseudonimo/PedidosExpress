package com.example.pedidosexpress.views.consumidor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.Login
import com.example.pedidosexpress.views.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CuentaConsumidor : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerConsumidor

    private val PREFS_USER_ID_KEY = "user_id"
    private val PREFS_USER_ROLE_KEY = "user_rol"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuenta_consumidor)

        bottomNavigationHandler = BottomNavigationHandlerConsumidor(this)

        val btnfav = findViewById<Button>(R.id.btnFavoritos)
        val txtcerrarsesion = findViewById<TextView>(R.id.logout)
        val btnhistpedidos = findViewById<Button>(R.id.btnHistorial)
        val btnpagos =findViewById<TextView>(R.id.pagos)

        btnfav.setOnClickListener {
            startActivity(Intent(this, FavoritosConsumidorActivity::class.java))
        }

        txtcerrarsesion.setOnClickListener {
            val btnpagos =findViewById<TextView>(R.id.pagos)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnhistpedidos.setOnClickListener {
            startActivity(Intent(this, Pedidos::class.java))
        }
        btnpagos.setOnClickListener {
            startActivity(Intent(this, MapaConsumidor::class.java))}
    }

    private fun clearSharedPreferences() {
        // Obtener el objeto SharedPreferences
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

        // Editar SharedPreferences para eliminar los valores almacenados
        val editor = sharedPreferences.edit()
        editor.remove(PREFS_USER_ID_KEY)
        editor.remove(PREFS_USER_ROLE_KEY)
        editor.apply()
    }
}
