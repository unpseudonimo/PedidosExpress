package com.example.pedidosexpress.views.consumidor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.Login
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Request
import org.json.JSONObject


class ProductoAdapter(private var productos: List<Producto>, private val userId: String) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {
    private lateinit var username: String


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenProducto: ImageView = itemView.findViewById(R.id.imagenProducto)
        val nombreProducto: TextView = itemView.findViewById(R.id.nombreProducto)
        val descripcionProducto: TextView = itemView.findViewById(R.id.descripcionProducto)
        val precioProducto: TextView = itemView.findViewById(R.id.precioProducto)
        val btnCraito: ExtendedFloatingActionButton = itemView.findViewById(R.id.btnAgregarCarrito)
        val btnFavorito: FloatingActionButton = itemView.findViewById(R.id.btnAgregarFavoritos)

    }
    private suspend fun enviarProductoFavorito(producto: Producto, context: Context): JSONObject? {
        val url = AppConfig.buildApiUrl("agregarFavorito")
        val json = Gson().toJson(producto)

        return try {
            withContext(Dispatchers.IO) {
                val response = OkHttpClient().newCall(
                    Request.Builder()
                        .url(url)
                        .post(RequestBody.create("application/json".toMediaTypeOrNull(), json))
                        .build()
                ).execute()

                if (response.isSuccessful) {
                    // Producto agregado a favoritos correctamente
                    Log.d("ProductoAdapter", "Producto agregado a favoritos correctamente")
                    JSONObject(response.body?.string())
                } else {
                    // Manejar error en la respuesta
                    Log.e("ProductoAdapter", "Error en la respuesta: ${response.code}")
                    null
                }
            }
        } catch (e: Exception) {
            // Manejar excepción
            Log.e("ProductoAdapter", "Error al enviar producto favorito", e)
            null
        }
    }


    private suspend fun enviarProductoAlServidor(producto: Producto) {
        val url = AppConfig.buildApiUrl("carrito")
        val json = Gson().toJson(producto)

        try {
            withContext(Dispatchers.IO) {
                val response = OkHttpClient().newCall(
                    Request.Builder()
                        .url(url)
                        .post(RequestBody.create("application/json".toMediaTypeOrNull(), json))
                        .build()
                ).execute()

                if (!response.isSuccessful) {
                    // Manejar error en la solicitud
                }
            }
        } catch (e: Exception) {
            // Manejar excepción
        }
    }

     //nuevos Solis
    fun actualizarProductos(nuevosProductos: List<Producto>) {
        // Actualiza la lista de productos y notifica al adaptador sobre el cambio
        productos = nuevosProductos
        notifyDataSetChanged()
    }

    //nuevos Solis

    fun limpiarRecomendaciones() {
        // Filtra los productos que no son recomendaciones
        productos = productos.filter { !it.esRecomendacion }
        notifyDataSetChanged()
    }

    // Método para agregar nuevas recomendaciones
    fun agregarRecomendaciones(recomendaciones: List<Producto>) {
        productos = productos + recomendaciones.map { it.copy(esRecomendacion = true) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombreProducto.text = producto.nombreProducto
        holder.descripcionProducto.text = producto.descripcionProducto
        holder.precioProducto.text = "Precio: ${producto.precioProducto}"


        // Construye la URL de la imagen utilizando el ID del producto
        val imageUrl = AppConfig.buildApiUrl("obtener_imagen/${producto.idProducto}")

        // Utiliza Picasso para cargar imágenes desde la URL
        Picasso.get().load(imageUrl).into(holder.imagenProducto)

        //nuevo solis favorito
        holder.btnFavorito.setOnClickListener {
            username = Login.getUsernameFromSharedPreferences(holder.itemView.context)
            CoroutineScope(Dispatchers.Main).launch {
                producto.nombreUsuario = username
                val response = enviarProductoFavorito(producto, holder.itemView.context)

                if (response?.get("status") == "error") {
                    Toast.makeText(
                        holder.itemView.context,
                        response["message"].toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        holder.itemView.context,
                        "Producto agregado a favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Configura un clic en el botón para enviar el producto al servidor
        holder.btnCraito.setOnClickListener {
            username = Login.getUsernameFromSharedPreferences(holder.itemView.context)
            Log.d("Username", "Valor de username: $username")
            if (username.isEmpty()) {
                // Si userId está vacío, muestra un mensaje para iniciar sesión
                Toast.makeText(holder.itemView.context,"Inicia sesión para agregar productos al carrito",Toast.LENGTH_SHORT).show()
                // Puedes redirigir a la pantalla de inicio de sesión aquí
            } else {
                producto.nombreUsuario=username
                if (producto.cantidadEnCarrito == 0) {
                    // Una vez que se ha agregado al carrito, establece la cantidad a 0
                    producto.cantidadEnCarrito = 1
                    // Si lo deseas, también puedes notificar al RecyclerView que los datos han cambiado
                    notifyDataSetChanged()
                    CoroutineScope(Dispatchers.Main).launch {
                        enviarProductoAlServidor(producto)
                        Toast.makeText(it.context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                        // Una vez que se ha agregado al carrito, establece la cantidad a 0
                        producto.cantidadEnCarrito = 0
                        // Si lo deseas, también puedes notificar al RecyclerView que los datos han cambiado
                        notifyDataSetChanged()
                    }
                } else {
                    val cantidadAnterior = producto.cantidadEnCarrito
                    CoroutineScope(Dispatchers.Main).launch {
                        enviarProductoAlServidor(producto)
                        Toast.makeText(it.context,"Producto agregado al carrito",Toast.LENGTH_SHORT).show()
                        // Una vez que se ha agregado al carrito, establece la cantidad a 0
                        producto.cantidadEnCarrito = 0
                        // Si lo deseas, también puedes notificar al RecyclerView que los datos han cambiado
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}