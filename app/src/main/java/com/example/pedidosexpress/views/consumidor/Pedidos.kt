package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.pedidosexpress.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Pedidos : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerConsumidor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        bottomNavigationHandler = BottomNavigationHandlerConsumidor(this)
    }

}