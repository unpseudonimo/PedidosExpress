package com.example.pedidosexpress.views.Repartidor;

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
class VehiculosRepartidorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehiculos_repartidor)

        // Ejemplo de encontrar vistas por sus ID
        val textViewVehiculo1: ImageView = findViewById(R.id.ivVehiculo1)
        val textViewVehiculo2: ImageView = findViewById(R.id.ivVehiculo2)
        val imageViewOtroVehiculo: ImageView = findViewById(R.id.ivVehiculo3)
        val buttonSeleccionarVehiculo: Button = findViewById(R.id.btnSeleccionarVehiculo)
        val spinnerTipoVehiculo: Spinner = findViewById(R.id.spinnerTipoVehiculo)

        // Puedes agregar más vistas según sea necesario

        // Ejemplo de configurar un clic en un botón
        buttonSeleccionarVehiculo.setOnClickListener {
            // Lógica para manejar el clic en el botón
            // Puedes acceder a las vistas y realizar acciones aquí
        }

        // Ejemplo de configurar selección de ítems en el spinner
        spinnerTipoVehiculo.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                val selectedType = parentView?.getItemAtPosition(position).toString()
                // Lógica para manejar la selección en el spinner
                // Puedes usar el valor de "selectedType" para realizar acciones específicas
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Lógica si no se selecciona nada en el spinner
            }
        })

        // Puedes agregar más configuraciones según sea necesario
    }
}