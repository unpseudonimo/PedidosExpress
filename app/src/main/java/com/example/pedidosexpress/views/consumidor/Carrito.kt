package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.model.Producto
import com.example.pedidosexpress.views.main.login
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.math.RoundingMode

class Carrito : AppCompatActivity(), CarritoAdapter.OnCantidadChangeListener {

    private lateinit var bottomNavigationHandler: BottomNavigationHandlerConsumidor

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalTextView: TextView
    private lateinit var apiService: ApiService
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
            .baseUrl("http://192.168.1.80:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val username = login.getUsernameFromSharedPreferences(this@Carrito)
        apiService = retrofit.create(ApiService::class.java)

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
            enviarDatosCompraAlServidor()
            startActivity(Intent(this@Carrito, Pedidos::class.java))
        }
        PagoBTN.setOnClickListener{
            startActivity(Intent(this@Carrito,MapaConsumidor::class.java))
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

    private fun enviarDatosCompraAlServidor() {
        val call = apiService.procesarCompra(obtenerProductosEnCarrito())

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@Carrito,
                        "Compra procesada con éxito",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Limpiar el carrito después de procesar la compra
                    (recyclerView.adapter as CarritoAdapter).eliminarTodosLosProductos()
                    updateTotal()
                } else {
                    Log.e("Carrito", "Error en el servidor: ${response.message()}")
                    Toast.makeText(this@Carrito, "Error en el servidor", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Carrito", "Error: ${t.message}")
                Toast.makeText(this@Carrito, "Error en la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun obtenerProductosEnCarrito(): List<CarritoData> {
        val adapter = recyclerView.adapter as CarritoAdapter
        return adapter.getProductos().map { producto ->
            CarritoData(
                producto.idProducto,
                producto.nombreProducto,
                producto.descripcionProducto,
                producto.imagenProducto,
                producto.precioProducto,
                producto.cantidadEnCarrito.toInt()
            )
        }
    }
}
