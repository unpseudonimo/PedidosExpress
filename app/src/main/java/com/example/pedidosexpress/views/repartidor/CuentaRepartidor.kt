package com.example.pedidosexpress.views.repartidor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.BottomNavigationHandler
import com.example.pedidosexpress.views.main.MainActivity
import com.example.pedidosexpress.views.main.RegistroFragment

class CuentaRepartidor : AppCompatActivity() {
    private lateinit var btnIniciarSesionConsumidor: Button
    private lateinit var btnRegistrarCuenta: Button

    private lateinit var bottomNavigationHandler: BottomNavigationHandler

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuenta_repartidor) // Establece el layout de la actividad
        // Inicializa el botón después de setContentView
        btnIniciarSesionConsumidor = findViewById<Button>(R.id.btnIniciarSesionConsumidor)
        btnRegistrarCuenta = findViewById<Button>(R.id.btnRegistrarCuenta)

        bottomNavigationHandler = BottomNavigationHandler(this)

        btnIniciarSesionConsumidor.setOnClickListener {
            startActivity(Intent(this@CuentaRepartidor,  MainActivity::class.java))
        }
        btnRegistrarCuenta.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RegistroFragment>(R.id.FragmentContainer)
            }
        }
    }
}