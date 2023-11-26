package com.example.pedidosexpress;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pedidosexpress.views.ConsumidorActivity;
import com.example.pedidosexpress.views.Home;
import com.example.pedidosexpress.views.RepartidorActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static final int MENU_HOME = R.id.home_item;
    public static final int MENU_CARRITO = R.id.car_item;
    public static final int MENU_USUARIO = R.id.user_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Ocultamos la barra de navegacion en esta clase


        setContentView(R.layout.activity_main); // Establece el layout de la actividad

        //Declaracion e inicializacion de variables
        Button btnIniciarSesionConsumidor = findViewById(R.id.btnIniciarSesionConsumidor);
        Button btnIniciarSesionAbarrotes = findViewById(R.id.btnIniciarSesionAbarrotes);
        Button btnSalir = findViewById(R.id.btnSalir);


        btnIniciarSesionConsumidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConsumidorActivity.class);
                startActivity(intent);
            }
        });

        // El segundo botón no navega y simplemente puede realizar otras acciones.
        btnIniciarSesionAbarrotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RepartidorActivity.class);
                startActivity(intent);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirmar Salida");
                builder.setMessage("¿Estás seguro de que deseas salir de la aplicación?");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Cierra la actividad
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Cierra el diálogo
                    }
                });
                builder.show();
            }
        });





    }
}
