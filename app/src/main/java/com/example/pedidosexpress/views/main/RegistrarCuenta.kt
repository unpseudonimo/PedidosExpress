package com.example.pedidosexpress.views.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.AppConfig
import org.json.JSONException
import org.json.JSONObject


class RegistrarCuenta : AppCompatActivity() {
    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var registerButton: Button? = null
    private val loginButton: Button? = null
    private var checkBoxConsumidor: RadioButton? = null
    private var checkBoxAbarrotes: RadioButton? = null
    private var roleRadioGroup: RadioGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registro)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        emailEditText = findViewById(R.id.emaildEditText)
        registerButton = findViewById(R.id.registerButton)
        checkBoxAbarrotes = findViewById(R.id.checkBoxAbarrotes)
        checkBoxConsumidor = findViewById(R.id.checkBoxConsumidor)
        roleRadioGroup = findViewById(R.id.roleRadioGroup)

        roleRadioGroup?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.checkBoxConsumidor -> checkBoxConsumidor?.isChecked = true
                R.id.checkBoxAbarrotes -> checkBoxAbarrotes?.isChecked = true
            }
        }
        // Realiza las configuraciones necesarias para la actividad RepartidorActivity si las tienes.
        val btnback = findViewById<ImageView>(R.id.btnback)
        registerButton?.setOnClickListener(View.OnClickListener {

            val url = AppConfig.buildApiUrl("registrar")
            val request: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST, url, null,
                Response.Listener { response ->
                    try {
                        val success = response.getBoolean("success")
                        val message = response.getString("message")
                        if (success) {
                            // Registro exitoso, puedes mostrar un mensaje al usuario
                            Toast.makeText(this@RegistrarCuenta, message, Toast.LENGTH_SHORT).show()

                            // Agregar un retraso de 2 segundos (2000 milisegundos) antes de cambiar de actividad
                            Handler().postDelayed({ // Código que se ejecutará después del retraso
                                val intent = Intent(this@RegistrarCuenta, Login::class.java)
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
                        // Manejar error en el formato de respuesta JSON
                    }
                },
                Response.ErrorListener { error ->
                    Log.e("RegistroCuenta", "Error en la solicitud al servidor: ${error.localizedMessage}")
                    Toast.makeText(this@RegistrarCuenta, "Error en la solicitud", Toast.LENGTH_SHORT).show()
                }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                override fun getBody(): ByteArray {
                    val params: MutableMap<String, String> = HashMap()
                    params["username"] = usernameEditText?.text.toString()
                    params["password"] = passwordEditText?.text.toString()
                    params["email"] = emailEditText?.text.toString()
                    // Agregar el rol seleccionado al JSON
                    params["rol"] = when (roleRadioGroup?.checkedRadioButtonId) {
                        R.id.checkBoxConsumidor -> "usuario"
                        R.id.checkBoxAbarrotes -> "repartidor"
                        else -> ""
                    }
                    return JSONObject(params as Map<*, *>).toString().toByteArray()
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