package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.RecuperarCuentaFragment

class ajustesConsumidorActivity : AppCompatActivity() {

    private lateinit var chkNotificaciones: CheckBox
    private lateinit var btnGuardarCambios: Button
    private lateinit var btnCambiarContrasena: Button
    private lateinit var btnBorrarFavoritos: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes_consumidor)

        // Inicializar vistas
        chkNotificaciones = findViewById(R.id.chkNotificaciones)
        btnGuardarCambios = findViewById<Button>(R.id.btnGuardarCambios)
        btnCambiarContrasena = findViewById<Button>(R.id.btnCambiarContrasena)
        btnBorrarFavoritos = findViewById<Button>(R.id.btnBorrarFavoritos)

        // Configurar clic en el botón "Guardar cambios"
        btnGuardarCambios.setOnClickListener {
            // Guardar cambios en la configuración, si es necesario
            // Puedes agregar tu lógica aquí
        }

        // Configurar clic en el botón "Cambiar Contraseña"
        btnCambiarContrasena.setOnClickListener {
            val intent = Intent(this, RecuperarCuentaFragment::class.java)
            startActivity(intent)
        }

        // Configurar clic en el botón "Borrar Favoritos"
        btnBorrarFavoritos.setOnClickListener {
            // Borrar la lista de favoritos, si es necesario
            // Puedes agregar tu lógica aquí
        }
    }
}
