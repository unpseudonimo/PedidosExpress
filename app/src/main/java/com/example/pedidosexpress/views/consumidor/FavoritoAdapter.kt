package com.example.pedidosexpress.views.consumidor

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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class FavoritoAdapter(private var productos: MutableList<Producto>) : RecyclerView.Adapter<FavoritoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenProducto: ImageView = itemView.findViewById(R.id.imagenProductoFav)
        val nombreProducto: TextView = itemView.findViewById(R.id.nombreProductoFav)
        val descripcionProducto: TextView = itemView.findViewById(R.id.descripcionProductoFav)
        val precioProducto: TextView = itemView.findViewById(R.id.precioProductoFav)
        val btnCraito: FloatingActionButton = itemView.findViewById(R.id.btnAgregarCarritoFav)
        val mostrarCantidad: TextView = itemView.findViewById(R.id.tvCantidadFav)
        val CantidadMenos: FloatingActionButton = itemView.findViewById(R.id.btnMenosFav)
        val CantidadMas: FloatingActionButton = itemView.findViewById(R.id.btnMasFav)
        val btnFavorito: FloatingActionButton = itemView.findViewById(R.id.btnQuitarFavoritos)
    }





    private suspend fun quitarProductoDeFavoritos(producto: Producto) {

        val url = AppConfig.buildApiUrl("quitarFavorito")
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
                    throw Exception("Error en la solicitud: ${response.code}")
                }

                // Cambiar al hilo principal para modificar la interfaz de usuario
                withContext(Dispatchers.Main) {
                    // Encuentra la posición del producto en la lista
                    val posicion = productos.indexOf(producto)

                    // Si la posición es válida, quita el producto de la lista
                    if (posicion != -1) {
                        productos.removeAt(posicion)
                        notifyItemRemoved(posicion)
                    }
                }
            }
        } catch (e: Exception) {
            // Manejar excepción
            Log.e("ProductoAdapter", "Error al quitar producto de favoritos", e)
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
                    throw Exception("Error en la solicitud: ${response.code}")
                }
            }
        } catch (e: Exception) {
            // Manejar excepción
            Log.e("ProductoAdapter", "Error al enviar producto al carrito", e)
        }
    }

    fun actualizarProductos(nuevosProductos: List<Producto>) {
        // Actualiza la lista de productos y notifica al adaptador sobre el cambio
        productos = nuevosProductos.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favoritos, parent, false)
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

        holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()

        // Configura los clics en los botones para actualizar la cantidad
        holder.CantidadMas.setOnClickListener {
            producto.cantidadEnCarrito++
            holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()
            // También puedes enviar la actualización al servidor aquí si es necesario
        }

        holder.btnFavorito.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                quitarProductoDeFavoritos(producto)
                Toast.makeText(
                    holder.itemView.context,
                    "Producto quitado de favoritos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.CantidadMenos.setOnClickListener {
            if (producto.cantidadEnCarrito > 0) {
                producto.cantidadEnCarrito--
                holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()
                // También puedes enviar la actualización al servidor aquí si es necesario
            }
        }

        // Configura un clic en el botón para enviar el producto al servidor
        holder.btnCraito.setOnClickListener {
            val username = Login.getUsernameFromSharedPreferences(holder.itemView.context)
            Log.d("Username", "Valor de username: $username")
            if (username.isEmpty()) {
                // Si userId está vacío, muestra un mensaje para iniciar sesión
                Toast.makeText(
                    holder.itemView.context,
                    "Inicia sesión para agregar productos al carrito",
                    Toast.LENGTH_SHORT
                ).show()
                // Puedes redirigir a la pantalla de inicio de sesión aquí
            } else {
                producto.nombreUsuario = username
                if (producto.cantidadEnCarrito == 0) {
                    // Una vez que se ha agregado al carrito, establece la cantidad a 0
                    producto.cantidadEnCarrito = 1
                    holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()
                    // Si lo deseas, también puedes notificar al RecyclerView que los datos han cambiado
                    notifyDataSetChanged()
                    CoroutineScope(Dispatchers.Main).launch {
                        enviarProductoAlServidor(producto)
                        Toast.makeText(
                            holder.itemView.context,
                            "Producto agregado al carrito",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Una vez que se ha agregado al carrito, establece la cantidad a 0
                        producto.cantidadEnCarrito = 0
                        holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()
                        // Si lo deseas, también puedes notificar al RecyclerView que los datos han cambiado
                        notifyDataSetChanged()
                    }
                } else {
                    val cantidadAnterior = producto.cantidadEnCarrito
                    CoroutineScope(Dispatchers.Main).launch {
                        enviarProductoAlServidor(producto)
                        Toast.makeText(
                            holder.itemView.context,
                            "Producto agregado al carrito",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Una vez que se ha agregado al carrito, establece la cantidad a 0
                        producto.cantidadEnCarrito = 0
                        holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()
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
