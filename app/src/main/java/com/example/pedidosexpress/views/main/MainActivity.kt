package com.example.pedidosexpress.views.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.pedidosexpress.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIniciarSesionConsumidor = findViewById<Button>(R.id.btnIniciarSesion)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        btnIniciarSesionConsumidor.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<Login>(R.id.FragmentContainer)
            }
        }

        btnSalir.setOnClickListener {
            showExitConfirmationDialog()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        showExitConfirmationDialog()
        // No llames a super.onBackPressed() aquí
    }

    private fun showExitConfirmationDialog() {
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("Confirmar Salida")
            .setMessage("¿Estás seguro de que deseas salir de la aplicación?")
            .setPositiveButton("Sí") { dialog, which ->
                finishAffinity()
                super.onBackPressed() // Mover la llamada aquí
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}