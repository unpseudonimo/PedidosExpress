package com.example.pedidosexpress.views.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.pedidosexpress.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject
import android.util.Log;
import com.android.volley.toolbox.Volley
import com.example.pedidosexpress.views.consumidor.AppConfig
import com.example.pedidosexpress.views.consumidor.HomeConsumidor
import com.example.pedidosexpress.views.repartidor.HomeRepartidor
import com.google.android.material.button.MaterialButton


class Login : Fragment() {
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private val PREFS_USER_ID_KEY = "user_id"
    private val PREFS_USER_ROLE_KEY = "user_rol"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Inicializar las variables
        usernameEditText = view.findViewById(R.id.usernameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        btnLogin = view.findViewById(R.id.btnIniciarSesion)
        // Configurar el OnClickListener para el botón de login
        btnLogin.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validar que se ingresen ambos campos
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Realizar la solicitud al servidor
            loginToServer(username, password)
        }

        val btnBack: FloatingActionButton = view.findViewById<FloatingActionButton>(R.id.btnback)

        // Configura un OnClickListener para el botón de retroceso
        btnBack.setOnClickListener {val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        // Configura un OnClickListener para el botón createAccountLink
        val createAccountLink: TextView = view.findViewById(R.id.createAccountLink)
        createAccountLink.setOnClickListener {
            val intent = Intent(requireContext(), RegistrarCuenta::class.java)
            startActivity(intent)
        }

        return view
    }
    fun saveUsernameToSharedPreferences(username: String, role: String) {
        // Obtener el objeto SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

        // Editar SharedPreferences para almacenar el nombre del usuario
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_USER_ID_KEY, username)
        editor.putString(PREFS_USER_ROLE_KEY, role)
        editor.apply()
    }

    companion object {
        private const val PREFS_USER_ID_KEY = "user_id"
        private const val PREFS_USER_ROLE_KEY = "user_rol"
        fun getUsernameFromSharedPreferences(context: Context): String {
            val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            return sharedPreferences.getString(PREFS_USER_ID_KEY, "") ?: ""
        }
    }
    private fun loginToServer(username: String, password: String) {
        val url = AppConfig.buildApiUrl("login") // Ajusta la URL según tu servidor
        // Crear una solicitud de cadena (StringRequest) utilizando Volley
        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    // Manejar la respuesta del servidor (puede ser un objeto JSON)
                    val jsonResponse = JSONObject(response)
                    val success = jsonResponse.getBoolean("success")
                    val message = jsonResponse.getString("message")

                    if (success) {
                        val userId = jsonResponse.getString("user_id")
                        val userRole = jsonResponse.getString("rol")
                        // Login exitoso, puedes realizar acciones adicionales o navegar a otra actividad
                        Toast.makeText(requireContext(), "Login exitoso. User ID: $userId. Rol Usuario: $userRole", Toast.LENGTH_SHORT).show()
                        // Guardar el nombre de usuario en SharedPreferences
                        saveUsernameToSharedPreferences(username,userRole)
                        when (userRole) {
                            "usuario" -> {
                                val intent = Intent(requireContext(), HomeConsumidor::class.java)
                                startActivity(intent)
                            }
                            "repartidor" -> {
                                val intent = Intent(requireContext(), HomeRepartidor::class.java)
                                startActivity(intent)
                            }
                        }

                    } else {
                        // Error en el login, muestra un mensaje de error
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    // Manejar errores en el formato de la respuesta JSON
                }
            },
            Response.ErrorListener { error ->
                Log.e("login", "Error en la solicitud al servidor: ${error.localizedMessage}")
                Toast.makeText(requireContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show()
            }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                val params: MutableMap<String, String> = HashMap()
                params["username"] = usernameEditText?.text.toString()
                params["password"] = passwordEditText?.text.toString()
                return JSONObject(params as Map<*, *>).toString().toByteArray()
            }
        }

        // Agregar la solicitud a la cola de Volley
        val queue = Volley.newRequestQueue(requireContext())
        queue.add(request)
    }

}