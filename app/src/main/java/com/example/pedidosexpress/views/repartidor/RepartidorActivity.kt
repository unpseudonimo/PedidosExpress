package com.example.pedidosexpress.views.Repartidor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.RecuperarCuenta
import com.example.pedidosexpress.views.main.RegistrarCuenta
//
class RepartidorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login_r)

        val btnIniciarR = findViewById<Button>(R.id.btnIniciarR)
        val btnAddCuenta = findViewById<TextView>(R.id.createAccountLink)
        val btnRecuperarPw = findViewById<TextView>(R.id.forgotPasswordLink)
        val btnback = findViewById<ImageView>(R.id.btnback)

        btnIniciarR.setOnClickListener {
            startActivity(Intent(this@RepartidorActivity, CuentaRepartidor::class.java))
        }
        btnAddCuenta.setOnClickListener { // Inicia la actividad RegistrarCuenta.java
            startActivity(Intent(this@RepartidorActivity, RegistrarCuenta::class.java))
        }
        btnRecuperarPw.setOnClickListener { // Inicia la actividad RegistrarCuenta.java
            startActivity(Intent(this@RepartidorActivity, RecuperarCuenta::class.java))
        }
        btnback.setOnClickListener {
            onBackPressed()
        }
    }

}