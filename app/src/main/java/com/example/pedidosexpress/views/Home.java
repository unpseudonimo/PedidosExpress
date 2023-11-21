package com.example.pedidosexpress.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pedidosexpress.MainActivity;
import com.example.pedidosexpress.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Home extends AppCompatActivity {
    public static final int MENU_HOME = R.id.home_item;
    public static final int MENU_CARRITO = R.id.car_item;
    public static final int MENU_USUARIO = R.id.user_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home); // Establece el layout o interfaz de la actividad

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
                            Intent intent = new Intent(Home.this, Home.class);
                            startActivity(intent);
                            return true;
                        }if (itemId == MENU_CARRITO) {
                            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
                            Intent intent = new Intent(Home.this, Home.class);
                            startActivity(intent);
                            return true;
                        }if (itemId == MENU_USUARIO) {
                            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
                            Intent intent = new Intent(Home.this, MainActivity.class);
                            startActivity(intent);
                            return true;
                        } else {
                            // Agrega más casos según sea necesario para otros ítems del menú
                            return false;
                        }
                    }


                });
    }

}
