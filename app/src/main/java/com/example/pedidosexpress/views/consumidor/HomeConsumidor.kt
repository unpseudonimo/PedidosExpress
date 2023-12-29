package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.MainActivity
import com.example.pedidosexpress.views.main.login
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeConsumidor : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeconsumidor) // Establece el layout o interfaz de la actividad

        //lista de productos:
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.setLayoutManager(layoutManager);

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.193:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.obtenerDatos()

        call.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val productos = response.body()
                    if (productos != null) {
                        val userId = login.getUsernameFromSharedPreferences(applicationContext) // Asegúrate de tener acceso al contexto aquí
                        val adapter = ProductoAdapter(productos, userId)
                        recyclerView.adapter = adapter
                    }
                } else {
                    Log.e("MainActivity", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("MainActivity", "Error: ${t.message}")
            }
        })



        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            handleNavigation(
                item
            )
        }
        // Configurar el fragmento de inicio al inicio
        loadFragment(HomeCFragment())
    }

    // Boton de Navegación
    fun handleNavigation(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == MENU_HOME) {
            return true
        }
        if (itemId == MENU_CARRITO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@HomeConsumidor, Carrito::class.java))
            return true
        }
        return if (itemId == MENU_USUARIO) {
            // Verificar si el usuario está logeado antes de navegar a CuentaConsumidor
            val username = login.getUsernameFromSharedPreferences(applicationContext)
            if (username.isEmpty()) {
                // Si userId está vacío, muestra un mensaje para iniciar sesión
                Toast.makeText(applicationContext, "Inicia sesión para acceder a tu cuenta", Toast.LENGTH_SHORT).show()
                // Puedes redirigir a la pantalla de inicio de sesión aquí
                startActivity(Intent(this@HomeConsumidor, MainActivity::class.java))
                false
            } else {
                // Usuario logeado, permite acceder a la actividad CuentaConsumidor
                startActivity(Intent(this@HomeConsumidor, CuentaConsumidor::class.java))
                true
            }
        } else {
            // Agrega más casos según sea necesario para otros ítems del menú
            false
        }
    }

    private fun loadFragment(fragment: HomeCFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.inicio_item, fragment)
            .commit()
    }
    companion object {
        val MENU_HOME = R.id.inicio_item
        val MENU_CARRITO = R.id.carrito_item
        val MENU_USUARIO = R.id.cuenta_item
    }
}