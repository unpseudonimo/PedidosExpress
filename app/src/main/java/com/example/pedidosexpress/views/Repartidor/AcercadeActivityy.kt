package com.example.pedidosexpress.views.Repartidor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class AcercadeActivityy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acercade_activityy)

        // Referencia al Floating Action Button
        val fabAction: FloatingActionButton = findViewById(R.id.fabAction)

        // Configuración del evento de clic del Floating Action Button
        fabAction.setOnClickListener {
            // Acción que se ejecutará al hacer clic en el botón
            mostrarMensaje("Haz hecho clic en el botón de acción.")
        }
    }

    // Método para mostrar un mensaje usando un Snackbar
    private fun mostrarMensaje(mensaje: String) {
        val rootView = window.decorView.rootView
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }
}
