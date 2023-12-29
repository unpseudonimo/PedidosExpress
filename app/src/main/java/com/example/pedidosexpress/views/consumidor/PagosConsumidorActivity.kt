package com.example.pedidosexpress.views.consumidor

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R

class   PagosConsumidorActivity : AppCompatActivity() {

    private lateinit var btnRealizarPago: Button
    private lateinit var tvMensajePago: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagos_consumidor)

        // Inicializar vistas
        btnRealizarPago = findViewById(R.id.btnRealizarPago)
        tvMensajePago = findViewById(R.id.tvMensajePago)

        // Configurar el evento de clic del botón
        btnRealizarPago.setOnClickListener {
            realizarPago()
        }
    }

    private fun realizarPago() {
        // Aquí puedes agregar lógica para realizar un pago
        // Por ejemplo, cambiar el texto del TextView
        tvMensajePago.text = "¡Pago realizado con éxito!"
    }
}
