package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.Login
import com.google.gson.Gson


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class Carrito : AppCompatActivity(), CarritoAdapter.OnCantidadChangeListener {
    private lateinit var bottomNavigationHandler: BottomNavigationHandlerConsumidor
    private lateinit var username: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalTextView: TextView
    private lateinit var PagoBTN: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        bottomNavigationHandler = BottomNavigationHandlerConsumidor(this)

        //lista de productos:
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Referencia al TextView del total
        totalTextView = findViewById(R.id.total)
        PagoBTN=findViewById(R.id.pago)


        val retrofit = Retrofit.Builder()
            .baseUrl(AppConfig.buildApiUrl(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        username = Login.getUsernameFromSharedPreferences(this@Carrito)
        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.obtenerCarito(username)

        call.enqueue(object : Callback<List<CarritoData>> {
            override fun onResponse(call: Call<List<CarritoData>>, response: Response<List<CarritoData>>) {
                if (response.isSuccessful) {
                    val productoscarrito = response.body()
                    Log.d("Carrito", "Cantidad de productos recibidos: ${productoscarrito?.size}")
                    if (productoscarrito != null) {
                        // Calcular la suma de cantidad * precio
                        val total = productoscarrito.sumByDouble { it.cantidadEnCarrito * it.precioProducto }
                        val totalFormateado = BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN).toString()
                        totalTextView.text = totalFormateado

                        // Configurar el adaptador con la interfaz de cambio de cantidad
                        val adapter = CarritoAdapter(productoscarrito.toMutableList(), this@Carrito)
                        recyclerView.adapter = adapter
                    }
                } else {
                    Log.e("MainActivity", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<CarritoData>>, t: Throwable) {
                Log.e("MainActivity", "Error: ${t.message}")
            }
        })


        val btnpedidos = findViewById<Button>(R.id.btnpedidos)


        btnpedidos.setOnClickListener {
            startActivity(Intent(this@Carrito, Pedidos::class.java))
        }
        PagoBTN.setOnClickListener{
            // URL fija que quieres abrir
            val url1 = AppConfig.buildApiUrl("verProductos")
            val url = AppConfig.buildApiUrl("realizar_pago")
            val json = Gson().toJson(username)
            val jsonString = json.toString()
            Thread {
                try {
                    val url = URL(url)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.doOutput = true
                    val os = connection.outputStream
                    os.write(jsonString.toByteArray(Charset.forName("UTF-8")))
                    os.close()
                    val responseCode = connection.responseCode
                    // Manejar la respuesta del servidor según sea necesario
                    runOnUiThread {
                        val intent = Intent(this@Carrito, WebViewActivity::class.java)
                        intent.putExtra("url", url1)
                        val pagoExitosoUrl = AppConfig.buildApiUrl("pago_exitoso")
                        intent.putExtra("pagoExitosoUrl", pagoExitosoUrl)  // URL a esperar para pago exitoso
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
            startActivity(Intent(this@Carrito, MapaConsumidor::class.java))
        }

    }
    override fun onCantidadChanged(producto: CarritoData) {
        // Aquí actualizas el total basándote en la nueva cantidad
        updateTotal()
    }

    private fun updateTotal() {
        val adapter = recyclerView.adapter as CarritoAdapter
        val productos = adapter.getProductos()

        // Calcular la suma de cantidad * precio
        val total = productos.sumByDouble { it.cantidadEnCarrito * it.precioProducto }
        val totalFormateado = BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN).toString()
        totalTextView.text = totalFormateado
    }

}
