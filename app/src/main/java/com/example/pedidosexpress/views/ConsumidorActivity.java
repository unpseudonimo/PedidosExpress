package com.example.pedidosexpress.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pedidosexpress.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
//import com.android.volley.Volley;

public class ConsumidorActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private ProgressDialog progressDialog;
    public static final String PREFS_USER_ID_KEY = "user_id"; // Clave para guardar y recuperar el user_id de SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumidor); // Establece el layout o interfaz de la actividad
        //CONFIGURACION DE BOTONES Y
        //Button btnIniciarC = findViewById(R.id.btnIniciarC);
        TextView btnAddCuenta = findViewById(R.id.createAccountLink);
        TextView btnRecuperarPw = findViewById(R.id.forgotPasswordLink);
        //TextView btnmapa = findViewById(R.id.btnmapa);
        ImageView btnback = findViewById(R.id.btnback);

        //CONFIGURACION DE EDITTEXT PARA EL LOGIN
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        progressDialog = new ProgressDialog(this);


        /*btnIniciarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumidorActivity.this, FeedConsumidor.class);
                startActivity(intent);
            }
        });*/

        btnAddCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad AddCuenta.java
                Intent intent = new Intent(ConsumidorActivity.this, AddCuenta.class);
                startActivity(intent);
            }
        });

        btnRecuperarPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad AddCuenta.java
                Intent intent = new Intent(ConsumidorActivity.this, Recuperacion.class);
                startActivity(intent);
            }
        });

        /*btnmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad AddCuenta.java
                Intent intent = new Intent(ConsumidorActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });*/

        // El botón Back simplemente puede regresar a la actividad pasada.
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Llama al método para regresar a la actividad anterior
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Esto es importante para mantener el comportamiento predeterminado de ir hacia atrás
        // Si no deseas realizar ninguna acción adicional, no es necesario agregar más código aquí.
    }

    public void login(View view) {
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        // Configura la política de red (esto no debe hacerse en el hilo principal)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // URL del servidor PHP
        String url = "https://curso-web-owl.tk/emmanuel_prueba/movil/login.php";

        // Muestra un diálogo de progreso
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        // Crea la solicitud POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss(); // Oculta el diálogo de progreso

                        // Maneja la respuesta del servidor
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss(); // Oculta el diálogo de progreso
                        // Maneja los errores de la solicitud
                        Toast.makeText(ConsumidorActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        // Agrega la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean success = jsonObject.getBoolean("success");

            if (success) {
                // Autenticación exitosa, obtén el user_id
                int userId = jsonObject.getInt("user_id");

                // Guarda el user_id en preferencias compartidas
                saveUserIdToSharedPreferences(userId);

                // Muestra un mensaje de éxito
                Toast.makeText(this, "Inicio de sesión exitoso. User ID: " + userId, Toast.LENGTH_SHORT).show();

                // Inicia la siguiente actividad (por ejemplo, 'tienda')
                Intent intent = new Intent(this, FeedConsumidor.class);
                startActivity(intent);
            } else {
                // Autenticación fallida, muestra un mensaje de error
                String message = jsonObject.getString("message");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveUserIdToSharedPreferences(int userId) {
        // Obtener el objeto SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        // Editar SharedPreferences para almacenar el user_id
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREFS_USER_ID_KEY, userId);
        editor.apply();
    }

    private int getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt(PREFS_USER_ID_KEY, -1); // Valor predeterminado -1 si no se encuentra el user_id
    }
}
