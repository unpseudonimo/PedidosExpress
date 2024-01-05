package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.common.Pedido
import com.example.pedidosexpress.views.consumidor.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistorialEntregas : AppCompatActivity() {
    private lateinit var bottomNavigationHandlerRepartidor: BottomNavigationHandlerRepartidor
    private lateinit var recyclerViewProductos: RecyclerView
    private lateinit var pedidoAdapter: PedidoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historialentregas)

        bottomNavigationHandlerRepartidor = BottomNavigationHandlerRepartidor(this)
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos)

        // Configurar el RecyclerView y el adaptador
        recyclerViewProductos.layoutManager = LinearLayoutManager(this)
        pedidoAdapter = PedidoAdapter(mutableListOf()) // Puedes iniciar con una lista vacía
        recyclerViewProductos.adapter = pedidoAdapter

        // Obtener la lista de pedidos desde el servidor
        obtenerHistorialDesdeServidor()
    }

    private fun obtenerHistorialDesdeServidor() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.70:5000") // Reemplazar con la URL correcta
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.obtenerHistorialEntregas()

        call.enqueue(object : Callback<List<Pedido>> {
            override fun onResponse(call: Call<List<Pedido>>, response: Response<List<Pedido>>) {
                if (response.isSuccessful) {
                    val historial = response.body()
                    // Actualizar el adaptador con la nueva lista de pedidos
                    historial?.let {
                        pedidoAdapter.actualizarPedidos(it)
                    }
                } else {
                    // Manejar errores de la respuesta HTTP
                    // Por ejemplo, puedes imprimir el código de error:
                    println("Error en la respuesta HTTP: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                // Manejar errores de la solicitud HTTP
                t.printStackTrace()
            }
        })
    }
}
