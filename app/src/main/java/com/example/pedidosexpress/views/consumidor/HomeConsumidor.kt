package com.example.pedidosexpress.views.consumidor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.MainActivity
import com.example.pedidosexpress.views.main.login
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeConsumidor : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerConsumidor
    private lateinit var recyclerView: RecyclerView
    private lateinit var etSearchProduct: EditText
    private lateinit var btnSearch: FloatingActionButton
    private lateinit var btnRecomendacion: FloatingActionButton
    private lateinit var adapter: ProductoAdapter

    private var productosBuscados: List<Producto> = emptyList()
    private var productosRecomendados: List<Producto> = emptyList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeconsumidor) // Establece el layout o interfaz de la actividad

        bottomNavigationHandler = BottomNavigationHandlerConsumidor(this)

        //lista de productos:
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager=layoutManager

        etSearchProduct = findViewById(R.id.etSearchProduct)
        btnSearch = findViewById(R.id.btnSearch)
        btnRecomendacion = findViewById(R.id.btnMostrar)

        // Inicializa el adaptador con una lista vacía
        adapter = ProductoAdapter(emptyList(), login.getUsernameFromSharedPreferences(applicationContext))
        recyclerView.adapter = adapter

        btnSearch.setOnClickListener {
            val searchTerm = etSearchProduct.text.toString()
            buscarProducto(searchTerm)
        }

        btnRecomendacion.setOnClickListener {
            val nombreProducto = etSearchProduct.text.toString()

            // Llama a la función para obtener recomendaciones
            obtenerRecomendaciones(nombreProducto)
        }
        cargarTodosLosProductos()
    }
    private fun cargarTodosLosProductos() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.80:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.obtenerDatos()

        call.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val productos = response.body()
                    if (productos != null) {
                        // Actualiza la lista de productos buscados
                        productosBuscados = productos
                        // Combina las listas de productos buscados y recomendados
                        val productosCombinados = productosBuscados + productosRecomendados
                        // Actualiza el adaptador con la lista combinada
                        adapter.actualizarProductos(productosCombinados)
                    }
                } else {
                    Log.e("HomeConsumidor", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("HomeConsumidor", "Error: ${t.message}")
            }
        })
    }
    private fun buscarProducto(nombreProducto: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.80:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.buscarProducto(nombreProducto)

        call.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {

                    val productos = response.body()
                    if (productos != null) {

                        // Actualiza la lista de productos buscados
                        productosBuscados = productos
                        // Combina las listas de productos buscados y recomendados
                        val productosCombinados = productosBuscados + productosRecomendados
                        // Actualiza el adaptador con la lista combinada
                        adapter.actualizarProductos(productosCombinados)
                    }
                } else {
                    Log.e("HomeConsumidor", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("HomeConsumidor", "Error: ${t.message}")
            }
        })
    }

    private fun obtenerRecomendaciones(selectedProducto: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.80:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getRecommendations(ProductoRequest(selectedProducto))

        call.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                if (response.isSuccessful) {
                    val recomendaciones = response.body()
                    if (recomendaciones != null) {
                        // Actualiza la lista de productos recomendados
                        productosRecomendados = recomendaciones
                        // Combina las listas de productos buscados y recomendados
                        val productosCombinados = productosBuscados + productosRecomendados
                        // Actualiza el adaptador con la lista combinada

                        adapter.actualizarProductos(productosCombinados)
                    }
                } else {
                    Log.e("HomeConsumidor", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("HomeConsumidor", "Error: ${t.message}")
            }
        })
    }
}