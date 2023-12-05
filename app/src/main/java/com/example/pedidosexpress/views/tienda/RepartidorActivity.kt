package com.example.pedidosexpress.views.tienda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.recuperacion.Recuperacion
import com.example.pedidosexpress.views.registro.AddCuenta

class RepartidorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repartidor)

        // DECLARACION DEL ID DE LOS BOTONES, Y LINKS
        val btnIniciarR = findViewById<Button>(R.id.btnIniciarR)
        val btnAddCuenta = findViewById<TextView>(R.id.createAccountLink)
        val btnRecuperarPw = findViewById<TextView>(R.id.forgotPasswordLink)
        val btnback = findViewById<ImageView>(R.id.btnback)

        // METODOS O ACCIONES A REALIZAR
        btnIniciarR.setOnClickListener {
            val intent = Intent(this@RepartidorActivity, FeedRepartidor::class.java)
            startActivity(intent)
        }
        btnAddCuenta.setOnClickListener { // Inicia la actividad AddCuenta.java
            val intent = Intent(this@RepartidorActivity, AddCuenta::class.java)
            startActivity(intent)
        }
        btnRecuperarPw.setOnClickListener { // Inicia la actividad AddCuenta.java
            val intent = Intent(this@RepartidorActivity, Recuperacion::class.java)
            startActivity(intent)
        }

        // El botón Back simplemente puede regresar a la actividad pasada.
        btnback.setOnClickListener {
            onBackPressed() // Llama al método para regresar a la actividad anterior
        }
    }

}