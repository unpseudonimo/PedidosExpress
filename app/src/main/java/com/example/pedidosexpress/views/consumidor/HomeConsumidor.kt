package com.example.pedidosexpress.views.consumidor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.Login
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class HomeConsumidor : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerConsumidor
    private lateinit var recyclerView: RecyclerView
    private lateinit var etSearchProduct: EditText
    private lateinit var btnSearch: FloatingActionButton
    private lateinit var btnRecomendacion: FloatingActionButton
    private lateinit var adapter: ProductoAdapter

    private var productosBuscados: List<Producto> = emptyList()
    private var productosRecomendados: List<Producto> = emptyList()

    private val CODIGO_DE_PERMISOS = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeconsumidor)
        // Obtén la instancia de BottomNavigationHandlerConsumidor
        bottomNavigationHandler = BottomNavigationHandlerConsumidor.getInstance(this)

        // Llama a clearSelectedItem desde cualquier parte de tu código
        BottomNavigationHandlerConsumidor.clearSelectedItem()

        bottomNavigationHandler = BottomNavigationHandlerConsumidor(this)

        // Verificar y solicitar permisos
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    CODIGO_DE_PERMISOS
                )
            }
        }

        // Verificar y solicitar activación del GPS
        verificarYActivarGPS()

        // Resto del código de tu actividad
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        etSearchProduct = findViewById(R.id.etSearchProduct)
        btnSearch = findViewById(R.id.btnSearch)

        adapter = ProductoAdapter(emptyList(), Login.getUsernameFromSharedPreferences(applicationContext))
        recyclerView.adapter = adapter

        btnSearch.setOnClickListener {
            val searchTerm = etSearchProduct.text.toString()
            if (searchTerm.isNotEmpty()) {
                // Utilizamos una coroutine para realizar la búsqueda
                GlobalScope.launch(Dispatchers.Main) {
                    buscarProducto(searchTerm)
                    obtenerRecomendaciones(searchTerm)
                }
            } else {
                cargarTodosLosProductos()
            }
        }

        cargarTodosLosProductos()


    }

    private fun verificarYActivarGPS() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (!bottomNavigationHandler.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODIGO_DE_PERMISOS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso otorgado, puedes realizar acciones relacionadas con la ubicación
            } else {
                // Permiso denegado, informar al usuario o manejar la situación de alguna manera
            }
        }
    }
    private fun cargarTodosLosProductos() {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConfig.buildApiUrl(""))
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
            .baseUrl(AppConfig.buildApiUrl(""))
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
            .baseUrl(AppConfig.buildApiUrl(""))
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