package com.example.pedidosexpress.views.consumidor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.RecuperarCuenta
import com.example.pedidosexpress.views.main.RegistrarCuenta
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONException
import org.json.JSONObject

//import com.android.volley.Volley;
class ConsumidorActivity : AppCompatActivity() {
    private var usernameInputLayout: TextInputLayout? = null
    private var passwordInputLayout: TextInputLayout? = null
    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login_c) // Establece el layout o interfaz de la actividad
        //CONFIGURACION DE BOTONES Y
        //Button btnIniciarC = findViewById(R.id.btnIniciarC);
        val btnAddCuenta = findViewById<TextView>(R.id.createAccountLink)
        val btnRecuperarPw = findViewById<TextView>(R.id.forgotPasswordLink)
        //TextView btnmapa = findViewById(R.id.btnmapa);
        val btnback = findViewById<ImageView>(R.id.btnback)

        //CONFIGURACION DE EDITTEXT PARA EL LOGIN
        usernameInputLayout = findViewById(R.id.usernameTextInputLayout)
        passwordInputLayout = findViewById(R.id.passwordTextInputLayout)
        usernameEditText = usernameInputLayout?.editText
        passwordEditText = passwordInputLayout?.editText
        progressDialog = ProgressDialog(this)


        /*btnIniciarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumidorActivity.this, CuentaConsumidor.class);
                startActivity(intent);
            }
        });*/btnAddCuenta.setOnClickListener { // Inicia la actividad RegistrarCuenta.java
            val intent = Intent(this@ConsumidorActivity, RegistrarCuenta::class.java)
            startActivity(intent)
        }
        btnRecuperarPw.setOnClickListener { // Inicia la actividad RegistrarCuenta.java
            val intent = Intent(this@ConsumidorActivity, RecuperarCuenta::class.java)
            startActivity(intent)
        }

        /*btnmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad RegistrarCuenta.java
                Intent intent = new Intent(ConsumidorActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });*/

        // El botón Back simplemente puede regresar a la actividad pasada.
        btnback.setOnClickListener {
            onBackPressed() // Llama al método para regresar a la actividad anterior
        }
    }

    fun login(view: View?) {
        val username = usernameEditText!!.text.toString()
        val password = passwordEditText!!.text.toString()

        // Configura la política de red (esto no debe hacerse en el hilo principal)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // URL del servidor PHP
        val url = "https://curso-web-owl.tk/emmanuel_prueba/movil/login.php"

        // Muestra un diálogo de progreso
        progressDialog!!.setMessage("Iniciando sesión...")
        progressDialog!!.show()

        // Crea la solicitud POST
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                progressDialog!!.dismiss() // Oculta el diálogo de progreso

                // Maneja la respuesta del servidor
                handleResponse(response)
            },
            Response.ErrorListener {
                progressDialog!!.dismiss() // Oculta el diálogo de progreso
                // Maneja los errores de la solicitud
                Toast.makeText(this@ConsumidorActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["username"] = username
                params["password"] = password
                return params
            }
        }

        // Agrega la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun handleResponse(response: String) {
        try {
            val jsonObject = JSONObject(response)
            val success = jsonObject.getBoolean("success")
            if (success) {
                // Autenticación exitosa, obtén el user_id
                val userId = jsonObject.getInt("user_id")

                // Guarda el user_id en preferencias compartidas
                saveUserIdToSharedPreferences(userId)

                // Muestra un mensaje de éxito
                Toast.makeText(
                    this,
                    "Inicio de sesión exitoso. User ID: $userId",
                    Toast.LENGTH_SHORT
                ).show()

                // Inicia la siguiente actividad (por ejemplo, 'repartidor')
                val intent = Intent(this, HomeConsumidor::class.java)
                startActivity(intent)
            } else {
                // Autenticación fallida, muestra un mensaje de error
                val message = jsonObject.getString("message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun saveUserIdToSharedPreferences(userId: Int) {
        // Obtener el objeto SharedPreferences
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)

        // Editar SharedPreferences para almacenar el user_id
        val editor = sharedPreferences.edit()
        editor.putInt(PREFS_USER_ID_KEY, userId)
        editor.apply()
    }

    private val userIdFromSharedPreferences: Int
        private get() {
            val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
            return sharedPreferences.getInt(
                PREFS_USER_ID_KEY,
                -1
            ) // Valor predeterminado -1 si no se encuentra el user_id
        }

    companion object {
        const val PREFS_USER_ID_KEY =
            "user_id" // Clave para guardar y recuperar el user_id de SharedPreferences
    }
}