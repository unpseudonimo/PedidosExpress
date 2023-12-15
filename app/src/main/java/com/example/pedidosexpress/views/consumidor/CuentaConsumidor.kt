package com.example.pedidosexpress.views.consumidor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.MainActivity
import com.example.pedidosexpress.views.main.RegistroFragment
import com.example.pedidosexpress.views.main.registro.RegistrarCuenta

class CuentaConsumidor : AppCompatActivity() {

    // Mueve la declaración del botón aquí para evitar NullPointerException
    private lateinit var btnIniciarSesionConsumidor: Button
    private lateinit var btnRegistrarCuenta: Button
    private lateinit var btnRol: Button

    private lateinit var bottomNavigationHandler: BottomNavigationHandler

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuenta_consumidor)

        // Inicializa el botón después de setContentView
        btnIniciarSesionConsumidor = findViewById<Button>(R.id.btnIniciarSesionConsumidor)
        btnRegistrarCuenta = findViewById<Button>(R.id.btnRegistrarCuenta)
        btnRol = findViewById<Button>(R.id.btnRol)

        bottomNavigationHandler = BottomNavigationHandler(this)

        btnIniciarSesionConsumidor.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<LoginCFragment>(R.id.FragmentContainer)
            }
        }
        btnRegistrarCuenta.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RegistroFragment>(R.id.FragmentContainer)
            }
        }
        btnRol.setOnClickListener {
            startActivity(Intent(this@CuentaConsumidor, MainActivity::class.java))
        }

    }
}
