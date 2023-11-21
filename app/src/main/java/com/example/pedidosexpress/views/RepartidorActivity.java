package com.example.pedidosexpress.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pedidosexpress.MainActivity;
import com.example.pedidosexpress.R;

public class RepartidorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repartidor);

        // DECLARACION DEL ID DE LOS BOTONES, Y LINKS
        Button btnIniciarR = findViewById(R.id.btnIniciarR);
        TextView btnAddCuenta = findViewById(R.id.createAccountLink);
        TextView btnRecuperarPw = findViewById(R.id.forgotPasswordLink);
        ImageView btnback = findViewById(R.id.btnback);

        // METODOS O ACCIONES A REALIZAR
        btnIniciarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RepartidorActivity.this, FeedRepartidor.class);
                startActivity(intent);
            }
        });

        btnAddCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad AddCuenta.java
                Intent intent = new Intent(RepartidorActivity.this, AddCuenta.class);
                startActivity(intent);
            }
        });

        btnRecuperarPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad AddCuenta.java
                Intent intent = new Intent(RepartidorActivity.this, Recuperacion.class);
                startActivity(intent);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Esto es importante para mantener el comportamiento predeterminado de ir hacia atrás
        // Si no deseas realizar ninguna acción adicional, no es necesario agregar más código aquí.
    }

}
