package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.ApiService
import com.example.pedidosexpress.views.main.Pedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistorialEntregas : AppCompatActivity() {

    private lateinit var recyclerViewProductos: RecyclerView
    private lateinit var pedidoAdapter: PedidoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historialentregas)

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
            .baseUrl("URL_DE_TU_SERVIDOR_FLASK") // Reemplazar con la URL correcta
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.obtenerHistorialEntregas()

        call.enqueue(object : Callback<List<Pedido>> {
            override fun onResponse(call: Call<List<Pedido>>, response: Response<List<Pedido>>) {
                if (response.isSuccessful) {
                    val historial = response.body()
                    // Actualizar el adaptador con la nueva lista de pedidos
                    pedidoAdapter.actualizarPedidos(historial.orEmpty())
                } else {
                    // Manejar errores de la respuesta HTTP
                }
            }

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                // Manejar errores de la solicitud HTTP
            }
        })
    }
}
