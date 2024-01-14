package com.example.pedidosexpress.views.repartidor

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedidosexpress.adapters.PedidoEntregadoAdapter
import com.example.pedidosexpress.databinding.ActivityHistorialentregasBinding
import com.example.pedidosexpress.model.PedidoEntregado
import com.example.pedidosexpress.views.consumidor.ApiService
import com.example.pedidosexpress.views.consumidor.AppConfig
import com.example.pedidosexpress.views.main.Login
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class HistorialEntregas : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerRepartidor
    private lateinit var binding: ActivityHistorialentregasBinding
    private lateinit var pedidoAdapter: PedidoEntregadoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorialentregasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationHandler = BottomNavigationHandlerRepartidor(this)
        // Configuración del RecyclerView
        pedidoAdapter = PedidoEntregadoAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = pedidoAdapter

        // Ejemplo de lista de pedidos (reemplaza esto con tu lógica para obtener los pedidos)
        obtenerListaDePedidos()
    }

    private fun obtenerListaDePedidos() {
        val username = Login.getUsernameFromSharedPreferences(this)

        if (username.isNullOrEmpty()) {
            // Manejar el caso en que el nombre de usuario no esté disponible
            return
        }

        lifecycleScope.launch {
            try {
                val listaDePedidos = withContext(Dispatchers.IO) {
                    val apiService = Retrofit.Builder()
                        .baseUrl(AppConfig.buildApiUrl(""))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ApiService::class.java)

                    val call: Call<List<PedidoEntregado>> = apiService.obtenerHistorialPedidos(username)
                    val response = call.execute()

                    if (response.isSuccessful) {
                        response.body() ?: emptyList()
                    } else {
                        // Manejar respuesta no exitosa
                        Log.e("HistorialEntregas", "Error en la respuesta: ${response.code()}")
                        emptyList()
                    }
                }

                // Actualizar el adaptador con la lista de pedidos
                pedidoAdapter.setPedidos(listaDePedidos)
                Log.d("HistorialEntregas", "Lista de pedidos: $listaDePedidos")
            } catch (e: IOException) {
                // Manejar excepción de red
                Log.e("HistorialEntregas", "Error de red: ${e.message}")
            }
        }
    }
}
