package com.example.pedidosexpress.views.Repartidor

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.google.android.material.snackbar.Snackbar

class AdministrarCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrar_cuenta)

        // Ejemplo de cómo mostrar un Snackbar al hacer clic en un botón (reemplaza con tus elementos):
        val btnGuardarCambios: Button = findViewById(R.id.btnGuardarCambios)
        btnGuardarCambios.setOnClickListener {
            guardarCambiosEnCuenta()
        }
    }

    // Método para guardar los cambios en la cuenta
    private fun guardarCambiosEnCuenta() {
        // Aquí implementa la lógica para guardar los cambios en la cuenta
        val adminCuentaLayout: View = findViewById(R.id.btnGuardarCambios) // Reemplaza con el ID correcto si es necesario
        Snackbar.make(
            adminCuentaLayout,
            "Cambios guardados exitosamente",
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
