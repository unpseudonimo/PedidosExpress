package com.example.pedidosexpress.views.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.pedidosexpress.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Ocultamos la barra de navegacion en esta clase
        setContentView(R.layout.activity_main) // Establece el layout de la actividad

        //Declaracion e inicializacion de variables
        val btnIniciarSesionConsumidor = findViewById<Button>(R.id.btnIniciarSesionConsumidor)

        val btnSalir = findViewById<Button>(R.id.btnSalir)

        // Manejar clics en los botones para mostrar fragmentos
        btnIniciarSesionConsumidor.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<login>(R.id.FragmentContainer)
            }
        }


        btnSalir.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Confirmar Salida")
            builder.setMessage("¿Estás seguro de que deseas salir de la aplicación?")
            builder.setPositiveButton("Sí") { dialog, which ->
                finishAffinity() // Cierra la actividad
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss() // Cierra el diálogo
            }
            builder.show()
        }
    }
}