package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.BottomNavigationHandlerConsumidor

class HomeRepartidor : AppCompatActivity() {

    private lateinit var bottomNavigationHandlerConsumidor: BottomNavigationHandlerRepartidor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homerepartidor) // Establece el layout o interfaz de la actividad

        bottomNavigationHandlerConsumidor = BottomNavigationHandlerRepartidor(this)

    }
}