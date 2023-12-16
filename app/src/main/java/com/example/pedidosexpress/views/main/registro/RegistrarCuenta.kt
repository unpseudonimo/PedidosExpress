package com.example.pedidosexpress.views.main.registro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.CuentaConsumidor
import org.json.JSONException
import org.json.JSONObject

class RegistrarCuenta : AppCompatActivity() {
    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var registerButton: Button? = null
    private val loginButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registro)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        emailEditText = findViewById(R.id.emaildEditText)
        registerButton = findViewById(R.id.registerButton)
        // Realiza las configuraciones necesarias para la actividad RepartidorActivity si las tienes.
        val btnback = findViewById<ImageView>(R.id.btnback)
        registerButton?.setOnClickListener(View.OnClickListener {
            val url = "https://curso-web-owl.tk/emmanuel_prueba/movil/registro_user.php"
            val request: StringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    try {
                        val jsonResponse = JSONObject(response)
                        val success = jsonResponse.getBoolean("success")
                        val message = jsonResponse.getString("message")
                        if (success) {
                            // Registro exitoso, puedes mostrar un mensaje al usuario
                            Toast.makeText(this@RegistrarCuenta, message, Toast.LENGTH_SHORT).show()

                            // Agregar un retraso de 2 segundos (2000 milisegundos) antes de cambiar de actividad
                            Handler().postDelayed({ // Código que se ejecutará después del retraso
                                val intent = Intent(this@RegistrarCuenta, CuentaConsumidor::class.java)
                                startActivity(intent)

                                // Asegúrate de cerrar la actividad actual si no quieres que el usuario pueda regresar a ella con el botón "Atrás"
                                finish()
                            }, 2000) // 2000 milisegundos (2 segundos)
                        } else {
                            // Error en el registro, muestra un mensaje de error
                            Toast.makeText(this@RegistrarCuenta, message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        // Error en el formato de la respuesta JSON
                    }
                },
                Response.ErrorListener { // Manejar errores de la solicitud
                    Toast.makeText(this@RegistrarCuenta, "Error en la solicitud", Toast.LENGTH_SHORT)
                        .show()
                }) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["username"] = usernameEditText?.getText().toString()
                    params["password"] = passwordEditText?.getText().toString()
                    params["email"] = emailEditText?.getText().toString()
                    return params
                }
            }
            val queue = Volley.newRequestQueue(this@RegistrarCuenta)
            queue.add(request)
        })

        // El botón Back simplemente puede regresar a la actividad pasada.
        btnback.setOnClickListener {
            onBackPressed() // Llama al método para regresar a la actividad anterior
        }
    }
}