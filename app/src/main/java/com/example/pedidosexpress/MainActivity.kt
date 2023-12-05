package com.example.pedidosexpress

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.views.consumidor.ConsumidorActivity
import com.example.pedidosexpress.views.tienda.RepartidorActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Ocultamos la barra de navegacion en esta clase
        setContentView(R.layout.activity_main) // Establece el layout de la actividad

        //Declaracion e inicializacion de variables
        val btnIniciarSesionConsumidor = findViewById<Button>(R.id.btnIniciarSesionConsumidor)
        val btnIniciarSesionAbarrotes = findViewById<Button>(R.id.btnIniciarSesionAbarrotes)
        val btnSalir = findViewById<Button>(R.id.btnSalir)
        btnIniciarSesionConsumidor.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    ConsumidorActivity::class.java
                )
            )
        }

        // El segundo botón no navega y simplemente puede realizar otras acciones.
        btnIniciarSesionAbarrotes.setOnClickListener {
            val intent = Intent(this@MainActivity, RepartidorActivity::class.java)
            startActivity(intent)
        }
        btnSalir.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Confirmar Salida")
            builder.setMessage("¿Estás seguro de que deseas salir de la aplicación?")
            builder.setPositiveButton("Sí") { dialog, which ->
                finish() // Cierra la actividad
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss() // Cierra el diálogo
            }
            builder.show()
        }
    }
}