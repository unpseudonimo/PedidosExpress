package com.example.pedidosexpress.views.consumidor

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.math.RoundingMode


class FavoritosConsumidorActivity : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerConsumidor
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos_consumidor)

        bottomNavigationHandler = BottomNavigationHandlerConsumidor(this)

        // Lista de productos:
        recyclerView = findViewById(R.id.recyclerView)

        // Usar GridLayoutManager con 2 columnas
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        val retrofit = Retrofit.Builder()
            .baseUrl(AppConfig.buildApiUrl(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val username = Login.getUsernameFromSharedPreferences(this)
        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.obtenerFavorito(username)

        call.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val productosFavoritos = response.body()
                    Log.d("Favoritos", "Cantidad de productos recibidos: ${productosFavoritos?.size}")
                    if (productosFavoritos != null) {
                        // Configurar el adaptador con la interfaz de cambio de cantidad
                        val adapter = FavoritoAdapter(productosFavoritos.toMutableList())
                        recyclerView.adapter = adapter
                    }
                } else {
                    Log.e("Favoritos", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("Favoritos", "Error: ${t.message}")
            }
        })
    }
    
}


