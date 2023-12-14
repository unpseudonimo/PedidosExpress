package com.example.pedidosexpress.views.consumidor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.mapa.MapaActivity

class CuentaConsumidor : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuenta_consumidor) // Establece el layout de la actividad

        val btnHistorialConsumidor = findViewById<Button>(R.id.btnHistorial)
        btnHistorialConsumidor.setOnClickListener {
            val  intent = Intent(this@CuentaConsumidor, HistorialConsumidorActivity::class.java)
            startActivity(intent)
        }

        val btnFavoritos = findViewById<Button>(R.id.btnFavoritos)
        btnFavoritos.setOnClickListener {
            val intent = Intent(this@CuentaConsumidor, FavoritosConsumidorActivity::class.java)
            startActivity(intent)
        }

        val notification = findViewById<TextView>(R.id.notificacion)
        notification.setOnClickListener {
            val intent = Intent(this@CuentaConsumidor, notificacionConsumidorActivity::class.java)
            startActivity(intent)
        }

        val btnpagos = findViewById<TextView>(R.id.btnpagos)
        btnpagos.setOnClickListener{
            val intent2 = Intent(this@CuentaConsumidor, PagosConsumidorActivity::class.java)
            startActivity(intent2)
        }

        val soporte = findViewById<TextView>(R.id.soporte)
        soporte.setOnClickListener {
            val intent=Intent(this@CuentaConsumidor, soporteConsumidorActivity::class.java)
            startActivity(intent)
        }

        val btnmapa = findViewById<TextView>(R.id.btnmapa)
        btnmapa.setOnClickListener {
            val intent1 = Intent(this@CuentaConsumidor, MapaActivity::class.java)
            startActivity(intent1)
        }

        val recomendacion =findViewById<TextView>(R.id.recomendacion)
        recomendacion.setOnClickListener {
            val intent = Intent( this@CuentaConsumidor, recomendacionConsumidorActivity::class.java)
            startActivity(intent)
        }

        val btnInvitar_amigos = findViewById<TextView>(R.id.btnInvitar_amigos)
        btnInvitar_amigos.setOnClickListener {
            val intent1 = Intent(this@CuentaConsumidor, InvitarAmigosActivity::class.java)
            startActivity(intent1)
        }

        val ajustes =  findViewById<TextView>(R.id.ajustes)
        ajustes.setOnClickListener {
            val intent =Intent(this@CuentaConsumidor, ajustesConsumidorActivity::class.java)
            startActivity(intent)
        }

    }
}