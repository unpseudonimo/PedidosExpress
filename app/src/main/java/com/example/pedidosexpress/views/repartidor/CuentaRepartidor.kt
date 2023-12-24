package com.example.pedidosexpress.views.repartidor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.BottomNavigationHandler
import com.example.pedidosexpress.views.main.MainActivity
//
class CuentaRepartidor : AppCompatActivity() {
    private lateinit var btnIniciarSesionConsumidor: Button
    private lateinit var btnRegistrarCuenta: Button

    private lateinit var bottomNavigationHandler: BottomNavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuenta_repartidor) // Establece el layout de la actividad
        // Inicializa el botón después de setContentView
        btnRegistrarCuenta = findViewById<Button>(R.id.btnRegistrarCuenta)

        bottomNavigationHandler = BottomNavigationHandler(this)

        btnIniciarSesionConsumidor.setOnClickListener {
            startActivity(Intent(this@CuentaRepartidor,  MainActivity::class.java))
        }
        /* btnRegistrarCuenta.setOnClickListener {
             supportFragmentManager.commit {
                 setReorderingAllowed(true)
                 add<RegistrarCuenta>(R.id.FragmentContainer)
             }
         }*/
    }
}