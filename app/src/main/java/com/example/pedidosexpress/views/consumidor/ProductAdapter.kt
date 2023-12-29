package com.example.pedidosexpress.views.consumidor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.login
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Request


class ProductoAdapter(private val productos: List<Producto>,private val userId: String) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenProducto: ImageView = itemView.findViewById(R.id.imagenProducto)
        val nombreProducto: TextView = itemView.findViewById(R.id.nombreProducto)
        val descripcionProducto: TextView = itemView.findViewById(R.id.descripcionProducto)
        val precioProducto: TextView = itemView.findViewById(R.id.precioProducto)
        val btnCraito: Button= itemView.findViewById(R.id.btnAgregarCarrito)
        val mostrarCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        val CantidadMenos: TextView = itemView.findViewById(R.id.btnMenos)
        val CantidadMas: TextView = itemView.findViewById(R.id.btnMas)

    }
    private suspend fun enviarProductoAlServidor(producto: Producto) {
        val url = "http://192.168.1.80:5000/carrito"
        val json = Gson().toJson(producto)

        try {
            withContext(Dispatchers.IO) {
                val response = OkHttpClient().newCall(
                    Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MediaType.parse("application/json"), json))
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
        val imageUrl = "http://192.168.1.80:5000/obtener_imagen/${producto.idProducto}"

        // Utiliza Picasso para cargar imágenes desde la URL
        Picasso.get().load(imageUrl).into(holder.imagenProducto)

        holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()

        // Configura los clics en los botones para actualizar la cantidad
        holder.CantidadMas.setOnClickListener {
            producto.cantidadEnCarrito++
            holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()
            // También puedes enviar la actualización al servidor aquí si es necesario
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
            val username = login.getUsernameFromSharedPreferences(holder.itemView.context)
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
                    holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()
                    // Si lo deseas, también puedes notificar al RecyclerView que los datos han cambiado
                    notifyDataSetChanged()
                    CoroutineScope(Dispatchers.Main).launch {
                        enviarProductoAlServidor(producto)
                        Toast.makeText(it.context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(it.context,"Producto agregado al carrito",Toast.LENGTH_SHORT).show()
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