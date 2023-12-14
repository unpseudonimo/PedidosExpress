package com.example.pedidosexpress.views.Repartidor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.FavoritosConsumidorActivity
import com.example.pedidosexpress.views.consumidor.HistorialConsumidorActivity
import com.example.pedidosexpress.views.consumidor.InvitarAmigosActivity
import com.example.pedidosexpress.views.consumidor.PagosConsumidorActivity
import com.example.pedidosexpress.views.consumidor.ajustesConsumidorActivity
import com.example.pedidosexpress.views.consumidor.notificacionConsumidorActivity
import com.example.pedidosexpress.views.consumidor.recomendacionConsumidorActivity
import com.example.pedidosexpress.views.consumidor.soporteConsumidorActivity
import com.example.pedidosexpress.views.mapa.MapaActivity

class CuentaRepartidor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuenta_repartidor) // Establece el layout de la actividad


        val btnHistorialRepartidor = findViewById<Button>(R.id.btnHistorialRepartidor)
        btnHistorialRepartidor.setOnClickListener {
            val  intent = Intent(this@CuentaRepartidor, HistorialRepartidorActivity::class.java)
            startActivity(intent)
        }

        val btnFavoritos = findViewById<Button>(R.id.btnFavoritos)
        btnFavoritos.setOnClickListener {
            val intent = Intent(this@CuentaRepartidor, FavoritosRepartidorActivity::class.java)
            startActivity(intent)
        }

        val notification = findViewById<TextView>(R.id.notificacionRepartidor)
        notification.setOnClickListener {
            val intent = Intent(this@CuentaRepartidor, notificacionRepartidorActivity::class.java)
            startActivity(intent)
        }

        val VehiculosRepartidor = findViewById<TextView>(R.id.VehiculosRepartidor)
        VehiculosRepartidor.setOnClickListener {
            val intent= Intent(this@CuentaRepartidor, VehiculosRepartidorActivity::class.java)
            startActivity(intent)
        }

        val pagosRepartidor = findViewById<TextView>(R.id.pagosRepartidor)
        pagosRepartidor.setOnClickListener{
            val intent2 = Intent(this@CuentaRepartidor, pagosRepartidorActivity::class.java)
            startActivity(intent2)
        }

         val soporteRepartidor =findViewById<TextView>(R.id.soporteRepartidor)
        soporteRepartidor.setOnClickListener {
            val intent = Intent( this@CuentaRepartidor, soporteRepartidorActivity::class.java)
            startActivity(intent)
        }
        val btnmapa = findViewById<TextView>(R.id.btnmapaRepartidor)
        btnmapa.setOnClickListener {
            val intent1 = Intent(this@CuentaRepartidor, MapaActivity::class.java)
            startActivity(intent1)
        }
        val AdministrarCuenta =findViewById<TextView>(R.id.AdministrarCuenta)
        AdministrarCuenta.setOnClickListener {
            val intent = Intent( this@CuentaRepartidor, AdministrarCuentaActivity::class.java)
            startActivity(intent)
        }

        val Acercade =findViewById<TextView>(R.id.Acercade)
        Acercade.setOnClickListener {
            val intent = Intent( this@CuentaRepartidor, AcercadeActivityy::class.java)
            startActivity(intent)
        }


        val PrivacidadySeguridad = findViewById<TextView>(R.id.PrivacidadySeguridad)
        PrivacidadySeguridad.setOnClickListener {
            val intent1 = Intent(this@CuentaRepartidor, PrivacidadySeguridadActivity::class.java)
            startActivity(intent1)
        }

        val Configuracion =  findViewById<TextView>(R.id.Configuracion)
        Configuracion.setOnClickListener {
            val intent = Intent(this@CuentaRepartidor, ConfiguracionActivity::class.java)
            startActivity(intent)
        }

    }
}