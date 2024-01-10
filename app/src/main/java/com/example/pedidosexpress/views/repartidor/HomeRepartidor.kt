package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.adapters.PedidoAdapter
import com.example.pedidosexpress.interfaces.IOnDetallePedidoClickListener
import com.example.pedidosexpress.model.PedidoAsignado
import com.example.pedidosexpress.views.consumidor.ApiService
import com.example.pedidosexpress.views.consumidor.AppConfig
import com.example.pedidosexpress.views.main.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeRepartidor : AppCompatActivity(), IOnDetallePedidoClickListener {

    private lateinit var bottomNavigationHandlerRepartidor: BottomNavigationHandlerRepartidor
    private lateinit var apiService: ApiService
    private lateinit var recyclerViewPedidos: RecyclerView
    private lateinit var pedidoAdapter: PedidoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homerepartidor)

        bottomNavigationHandlerRepartidor = BottomNavigationHandlerRepartidor(this)

        recyclerViewPedidos = findViewById(R.id.recyclerViewPedidos)

        // Inicializar el servicio Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConfig.buildApiUrl(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear una instancia del servicio ApiService
        apiService = retrofit.create(ApiService::class.java)

        // Configurar el RecyclerView y el adaptador
        recyclerViewPedidos.layoutManager = LinearLayoutManager(this)
        pedidoAdapter = PedidoAdapter(mutableListOf(), this)
        recyclerViewPedidos.adapter = pedidoAdapter

        // Obtener el nombre del repartidor desde SharedPreferences
        val nombreRepartidor = Login.getUsernameFromSharedPreferences(this)

        // Establecer el listener en el adaptador
        // Obtener los pedidos del repartidor directamente usando el nombre
        obtenerPedidosRepartidor(nombreRepartidor)
    }

    private fun obtenerPedidosRepartidor(nombreRepartidor: String) {
        val call = apiService.verPedidosRepartidor(nombreRepartidor)

        call.enqueue(object : Callback<List<PedidoAsignado>> {
            override fun onResponse(
                call: Call<List<PedidoAsignado>>,
                response: Response<List<PedidoAsignado>>
            ) {
                if (response.isSuccessful) {
                    val pedidosList = response.body()

                    if (pedidosList != null) {
                        pedidoAdapter.actualizarPedidos(pedidosList)
                        Log.d("HomeRepartidor", "Pedidos actualizados correctamente")
                    } else {
                        Log.d("HomeRepartidor", "No hay pedidos para mostrar")
                    }
                } else {
                    Log.e("HomeRepartidor", "Error en la respuesta HTTP: ${response.code()}")
                    // Puedes imprimir el mensaje de error si está disponible
                    response.errorBody()?.let {
                        try {
                            Log.e("HomeRepartidor", it.string())
                        } catch (e: Exception) {
                            Log.e("HomeRepartidor", "Error al obtener el mensaje de error")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<PedidoAsignado>>, t: Throwable) {
                Log.e("HomeRepartidor", "Error en la solicitud HTTP", t)
            }
        })

    }

    // Implementación de la interfaz OnDetallePedidoClickListener
    override fun onDetallePedidoClick(view: View) {
        // Maneja el evento de clic del botón "Ver Detalle" aquí
        // Puedes acceder al pedido asociado al clic utilizando el adaptador
    }
}
