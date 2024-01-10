package com.example.pedidosexpress.views.main

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

        // Ocultamos la barra de navegación en esta clase
        setContentView(R.layout.activity_main) // Establece el layout de la actividad

        // Declaracion e inicializacion de variables
        val btnIniciarSesionConsumidor = findViewById<Button>(R.id.btnIniciarSesion)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        // Manejar clics en los botones para mostrar fragmentos
        btnIniciarSesionConsumidor.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<Login>(R.id.FragmentContainer)
            }
        }

        btnSalir.setOnClickListener {
            // Construye el cuadro de diálogo con MaterialAlertDialogBuilder
            MaterialAlertDialogBuilder(this@MainActivity)
                .setTitle("Confirmar Salida")
                .setMessage("¿Estás seguro de que deseas salir de la aplicación?")
                .setPositiveButton("Sí") { dialog, which ->
                    finishAffinity() // Cierra la actividad
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss() // Cierra el diálogo
                }
                .show()
        }
    }
}
