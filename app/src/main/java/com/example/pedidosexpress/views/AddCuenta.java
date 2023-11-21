package com.example.pedidosexpress.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pedidosexpress.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCuenta extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, emailEditText;
    private Button registerButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevacuenta);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        registerButton = findViewById(R.id.registerButton);
        // Realiza las configuraciones necesarias para la actividad RepartidorActivity si las tienes.
        ImageView btnback = findViewById(R.id.btnback);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://curso-web-owl.tk/emmanuel_prueba/movil/registro_user.php";
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    String message = jsonResponse.getString("message");

                                    if (success) {
                                        // Registro exitoso, puedes mostrar un mensaje al usuario
                                        Toast.makeText(AddCuenta.this, message, Toast.LENGTH_SHORT).show();

                                        // Agregar un retraso de 2 segundos (2000 milisegundos) antes de cambiar de actividad
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Código que se ejecutará después del retraso
                                                Intent intent = new Intent(AddCuenta.this, FeedConsumidor.class);
                                                startActivity(intent);

                                                // Asegúrate de cerrar la actividad actual si no quieres que el usuario pueda regresar a ella con el botón "Atrás"
                                                finish();
                                            }
                                        }, 2000); // 2000 milisegundos (2 segundos)
                                    } else {
                                        // Error en el registro, muestra un mensaje de error
                                        Toast.makeText(AddCuenta.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    // Error en el formato de la respuesta JSON
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Manejar errores de la solicitud
                                Toast.makeText(AddCuenta.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", usernameEditText.getText().toString());
                        params.put("password", passwordEditText.getText().toString());
                        params.put("email", emailEditText.getText().toString());
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(AddCuenta.this);
                queue.add(request);
            }
        });

        // El botón Back simplemente puede regresar a la actividad pasada.
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Llama al método para regresar a la actividad anterior
            }
        });
    }


}
