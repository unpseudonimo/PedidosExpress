package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
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
        recyclerView.layoutManager = LinearLayoutManager(this)

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
                        val adapter = ProductoAdapter(productos)
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
            startActivity(Intent(this@HomeConsumidor, HomeConsumidor::class.java))
            return true
        }
        if (itemId == MENU_CARRITO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@HomeConsumidor, Carrito::class.java))
            return true
        }
        return if (itemId == MENU_USUARIO) {
            // Navegar a la otra actividad (puedes cambiar SecondActivity.class)
            startActivity(Intent(this@HomeConsumidor, CuentaConsumidor::class.java))
            true
        } else {
            // Agrega más casos según sea necesario para otros ítems del menú
            false
        }
    }

    private fun loadFragment(fragment: HomeCFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.home_item, fragment)
            .commit()
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.home_item) {
            // Acción para el botón "repartidor"
            Toast.makeText(this, "Seleccionaste repartidor", Toast.LENGTH_SHORT).show()
            // Crea un Intent para iniciar RepartidorActivity
            val intent = Intent(this, RepartidorActivity::class.java)
            startActivity(intent)
            true
        } else if (id == R.id.orders_item) {
            // Acción para el botón "Usuuario"
            Toast.makeText(this, "Seleccionaste Usuario", Toast.LENGTH_SHORT).show()
            true
        } else if (id == R.id.user_item) {
            // Acción para el botón "repartidor"
            Toast.makeText(this, "Saliendo...", Toast.LENGTH_SHORT).show()
            // Acción para el botón "Salir"
            finish() // Cierra la actividad
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }*/

    companion object {
        val MENU_HOME = R.id.home_item
        val MENU_CARRITO = R.id.orders_item
        val MENU_USUARIO = R.id.user_item
    }
}