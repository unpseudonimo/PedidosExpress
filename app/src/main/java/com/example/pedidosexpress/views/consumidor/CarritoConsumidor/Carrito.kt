package com.example.pedidosexpress.views.consumidor.CarritoConsumidor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.BottomNavigationHandler

class Carrito : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        bottomNavigationHandler = BottomNavigationHandler(this)

    }
}