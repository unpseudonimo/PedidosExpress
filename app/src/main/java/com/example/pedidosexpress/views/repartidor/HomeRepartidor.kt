package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import android.util.Log
import android.view.View  // Asegúrate de importar View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.common.Pedido
import com.example.pedidosexpress.views.consumidor.ApiService
import com.example.pedidosexpress.views.consumidor.Producto
import com.example.pedidosexpress.views.main.login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeRepartidor : AppCompatActivity(), OnDetallePedidoClickListener {

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

        // Establecer el listener en el adaptador
        pedidoAdapter.setOnDetallePedidoClickListener(this)

        // Obtener los pedidos del repartidor directamente usando el nombre
        obtenerPedidosRepartidor(nombreRepartidor)
    }

    private fun obtenerPedidosRepartidor(nombreRepartidor: String) {
        val call = apiService.verPedidosRepartidor(nombreRepartidor)

        call.enqueue(object : Callback<List<Pedido>> {
            override fun onResponse(
                call: Call<List<Pedido>>,
                response: Response<List<Pedido>>
            ) {
                if (response.isSuccessful) {
                    val pedidosList = response.body()

                    if (!pedidosList.isNullOrEmpty()) {
                        // Imprimir registros antes de actualizar el adaptador
                        for (pedido in pedidosList) {
                            Log.d("HomeRepartidor", "Pedido ID: ${pedido._id as? String ?: "ID no disponible"}")
                            Log.d("HomeRepartidor", "Nombre Cliente: ${pedido.nombre_cliente}")
                            Log.d("HomeRepartidor", "Nombre Repartidor: ${pedido.nombre_repartidor}")
                            Log.d("HomeRepartidor", "Productos: ${pedido.productos}")
                            Log.d("HomeRepartidor", "Total: ${pedido.total}")
                            Log.d("HomeRepartidor", "Estado: ${pedido.estado}")
                        }

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

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                Log.e("HomeRepartidor", "Error en la solicitud HTTP", t)
            }
        })
    }

    private fun obtenerIdPedido(id: Any?): String {
        return when (id) {
            is String -> id
            is Map<*, *> -> id["_id"]?.toString() ?: "ID no disponible"
            else -> "ID no disponible"
        }
    }

    // Implementación de la interfaz OnDetallePedidoClickListener
    override fun onDetallePedidoClick(view: View) {
        // Maneja el evento de clic del botón "Ver Detalle" aquí
        // Puedes acceder al pedido asociado al clic utilizando el adaptador
    }
}
