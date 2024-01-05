package com.example.pedidosexpress.views.repartidor

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.Producto
import com.example.pedidosexpress.views.consumidor.ProductoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class DetallePedido : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)

        // Obtener datos del Intent
        val intent = intent
        if (intent != null && intent.hasExtra("pedido_id")) {
            val pedidoId = intent.getStringExtra("pedido_id")

            // Aquí puedes usar el ID del pedido para cargar los detalles del pedido desde tu fuente de datos
            // Cargar detalles del pedido y productos asociados

            // Ejemplo de carga de productos
            val productos = obtenerProductosEjemplo() // Reemplaza esto con la lógica real para obtener productos

            // Configurar el RecyclerView con los productos
            val recyclerViewProductos: RecyclerView = findViewById(R.id.recyclerViewProductos)
            val productoAdapter = ProductoAdapter(productos, "") // Reemplaza "" con el ID de usuario real
            recyclerViewProductos.adapter = productoAdapter

            // Obtener una referencia del botón de retroceso
            val btnBack: FloatingActionButton = findViewById(R.id.btnback)

            // Agregar un listener al botón de retroceso
            btnBack.setOnClickListener {
                // Manejar el evento de clic (por ejemplo, cerrar la actividad)
                finish()
            }
        } else {
            // Manejar el caso en que no se proporciona el ID del pedido
            // ...
            finish() // Cerrar la actividad si no hay datos válidos
        }
    }

    // Método de ejemplo para obtener productos (reemplaza con la lógica real)
    private fun obtenerProductosEjemplo(): List<Producto> {
        return listOf(
            Producto(1, "Producto 1", "Descripción 1", "imagen1.jpg", 10.0, 0, ""),
            Producto(2, "Producto 2", "Descripción 2", "imagen2.jpg", 15.0, 0, ""),
            Producto(3, "Producto 3", "Descripción 3", "imagen3.jpg", 20.0, 0, "")
        )
    }
}
