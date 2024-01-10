package com.example.pedidosexpress.views.consumidor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pedidosexpress.R
import org.json.JSONException
import org.json.JSONObject

class AjustesConsumidorActivity : AppCompatActivity() {
    private lateinit var btnGuardarCambios: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes_consumidor)

        // Inicializar vistas

        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)

        // Configurar clic en el botón "Guardar cambios"

    }

    private fun enviarAlServidor( direccion: String, telefono: String) {
        val url = AppConfig.buildApiUrl("actualizar_usuario")

        val jsonObject = JSONObject()
        jsonObject.put("direccion", direccion)
        jsonObject.put("Telefono", telefono)

        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                // Manejar la respuesta del servidor según sea necesario
                Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                // Manejar el error de la solicitud
                Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            })

        // Añadir la solicitud a la cola de solicitudes
        Volley.newRequestQueue(this).add(request)
        btnGuardarCambios.setOnClickListener {
            // Obtener datos del EditText


            // Llamar a la función para enviar los datos al servidor
            enviarAlServidor(direccion, telefono)
        }
    }

}
