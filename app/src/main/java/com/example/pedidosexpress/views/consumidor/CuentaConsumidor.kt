package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.mapa.MapaActivity

class CuentaConsumidor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_consumidor) // Establece el layout de la actividad
        val btnmapa = findViewById<TextView>(R.id.btnmapa)
        btnmapa.setOnClickListener {
            val intent = Intent(this@CuentaConsumidor, MapaActivity::class.java)
            startActivity(intent)
        }
    }
}