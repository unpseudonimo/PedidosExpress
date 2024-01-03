package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.ApiService
import com.example.pedidosexpress.views.consumidor.Producto
import com.example.pedidosexpress.views.main.Pedido
import com.example.pedidosexpress.views.main.login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeRepartidor : AppCompatActivity() {

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
            .baseUrl("http://192.168.1.80:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear una instancia del servicio ApiService
        apiService = retrofit.create(ApiService::class.java)

        // Configurar el RecyclerView y el adaptador
        recyclerViewPedidos.layoutManager = LinearLayoutManager(this)
        pedidoAdapter = PedidoAdapter(mutableListOf())
        recyclerViewPedidos.adapter = pedidoAdapter

        // Obtener el nombre del repartidor desde SharedPreferences
        val nombreRepartidor = login.getUsernameFromSharedPreferences(this)

        // Obtener los pedidos del repartidor directamente usando el nombre
        obtenerPedidosRepartidor(nombreRepartidor)
    }

    private fun obtenerPedidosRepartidor(nombreRepartidor: String) {
        val call = apiService.verPedidosRepartidor(nombreRepartidor)

        call.enqueue(object : Callback<List<Map<String, Any>>> {
            override fun onResponse(
                call: Call<List<Map<String, Any>>>,
                response: Response<List<Map<String, Any>>>
            ) {
                if (response.isSuccessful) {
                    val pedidosMapList = response.body()

                    if (!pedidosMapList.isNullOrEmpty()) {
                        // Mapear la lista de Map<String, Any> a una lista de objetos Pedido
                        val pedidos = pedidosMapList.map { pedidoMap ->
                            Pedido(
                                pedidoMap["_id"]?.let { it as Map<String, Any> }?.get("\$oid")
                                    ?.toString() ?: "",
                                pedidoMap["nombre_cliente"]?.toString() ?: "",
                                pedidoMap["nombre_repartidor"]?.toString() ?: "",
                                (pedidoMap["productos"] as? List<Map<String, Any>>)?.map { productoMap ->
                                    Producto(
                                        (productoMap["id_producto"] as? Int) ?: 0,
                                        productoMap["nombre_producto"]?.toString() ?: "",
                                        (productoMap["precio_producto"] as? Double) ?: 0.0,
                                        (productoMap["cantidad"] as? Int) ?: 0
                                    )
                                } ?: emptyList(),
                                (pedidoMap["total"] as? Double) ?: 0.0,
                                pedidoMap["estado"]?.toString() ?: ""
                            )
                        }

                        // Actualizar la interfaz de usuario con la lista de pedidos
                        pedidoAdapter.actualizarPedidos(pedidos)
                        Log.d("HomeRepartidor", "Pedidos actualizados correctamente")
                    } else {
                        // Manejar el caso en que no hay pedidos para mostrar
                        Log.d("HomeRepartidor", "No hay pedidos para mostrar")
                    }
                } else {
                    // Manejar errores de la respuesta HTTP
                    Log.e("HomeRepartidor", "Error en la respuesta HTTP")
                }
            }

            override fun onFailure(call: Call<List<Map<String, Any>>>, t: Throwable) {
                // Manejar errores de la solicitud HTTP
            }
        })
    }

}
