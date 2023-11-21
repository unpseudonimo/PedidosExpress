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
        setContentView(R.layout.activity_main); // Establece el layout de la actividad
        Button btnIniciarSesionConsumidor = findViewById(R.id.btnIniciarSesionConsumidor);
        Button btnIniciarSesionAbarrotes = findViewById(R.id.btnIniciarSesionAbarrotes);
        Button btnSalir = findViewById(R.id.btnSalir);

        // Boton de Navegación
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();

                        if (itemId == MENU_HOME) {
                            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                            return true;
                        }if (itemId == MENU_CARRITO) {
                            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                            return true;
                        }if (itemId == MENU_USUARIO) {
                            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            return true;
                        } else {
                            // Agrega más casos según sea necesario para otros ítems del menú
                            return false;
                        }
                    }


                });

        // Configurar el fragmento de inicio al inicio
        loadFragment(new HomeFragment());


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

    private void loadFragment(HomeFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_item, fragment)
                .commit();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home_item) {
            // Acción para el botón "Repartidor"
            Toast.makeText(this, "Seleccionaste Repartidor", Toast.LENGTH_SHORT).show();
            // Crea un Intent para iniciar RepartidorActivity
            Intent intent = new Intent(this, RepartidorActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.car_item) {
            // Acción para el botón "Usuuario"
            Toast.makeText(this, "Seleccionaste Usuario", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.user_item) {
            // Acción para el botón "Repartidor"
            Toast.makeText(this, "Saliendo...", Toast.LENGTH_SHORT).show();
            // Acción para el botón "Salir"
            finish(); // Cierra la actividad
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
