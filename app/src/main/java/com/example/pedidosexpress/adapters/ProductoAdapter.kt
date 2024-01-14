package com.example.pedidosexpress.adapters

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
import com.example.pedidosexpress.model.ProductoData
import com.example.pedidosexpress.views.main.Login
import com.google.android.material.button.MaterialButton
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


class ProductoAdapter(private var productoData: List<ProductoData>, private val userId: String) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenProducto: ImageView = itemView.findViewById(R.id.imagenProducto)
        val nombreProducto: TextView = itemView.findViewById(R.id.nombreProducto)
        val descripcionProducto: TextView = itemView.findViewById(R.id.descripcionProducto)
        val precioProducto: TextView = itemView.findViewById(R.id.precioProducto)
        val btnCraito: ExtendedFloatingActionButton = itemView.findViewById(R.id.btnAgregarCarrito)
        val btnFavorito: MaterialButton = itemView.findViewById(R.id.btnAgregarFavoritos)

    }

    private fun esProductoEnFavoritos(productoData: ProductoData, listaProductos: List<ProductoData>): Boolean {
        // Filtra la lista de productos para obtener solo los favoritos
        val productosFavoritos = listaProductos.filter { it.esFavorito }
        // Verifica si el productoData actual está en la lista de favoritos
        return productosFavoritos.any { it.idProducto == productoData.idProducto }
    }

    private suspend fun enviarProductoFavorito(productoData: ProductoData, context: Context) {
        val url = "http://192.168.1.70:5000/agregarFavorito"
        val json = Gson().toJson(productoData)

        try {
            withContext(Dispatchers.IO) {
                val response = OkHttpClient().newCall(
                    Request.Builder()
                        .url(url)
                        .post(RequestBody.create("application/json".toMediaTypeOrNull(), json))
                        .build()
                ).execute()

                if (response.isSuccessful) {
                    // ProductoData agregado a favoritos correctamente
                    Log.d("ProductoAdapter", "ProductoData agregado a favoritos correctamente")
                    // Actualiza la propiedad esFavorito
                    productoData.esFavorito = true
                } else {
                    // Manejar error en la respuesta
                    if (response.code == 409) {
                        // Código 409 indica conflicto, es decir, el productoData ya existe en favoritos
                        Log.d("ProductoAdapter", "ProductoData ya está en favoritos")
                        // Puedes mostrar un mensaje aquí si lo deseas
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "ProductoData ya está en favoritos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Log.e("ProductoAdapter", "Error en la respuesta: ${response.code}")
                        // Puedes mostrar un mensaje de error aquí si lo deseas
                    }
                }
            }
        } catch (e: Exception) {
            // Manejar excepción
            Log.e("ProductoAdapter", "Error al enviar productoData favorito", e)
            // Puedes mostrar un mensaje de error aquí si lo deseas
        }
    }


    private suspend fun enviarProductoAlServidor(productoData: ProductoData) {
        val url = "http://192.168.1.70:5000/carrito"
        val json = Gson().toJson(productoData)

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

    fun actualizarProductos(nuevosProductoData: List<ProductoData>) {
        // Actualiza la lista de productoData y notifica al adaptador sobre el cambio
        productoData = nuevosProductoData
        notifyDataSetChanged()
    }

    fun limpiarRecomendaciones() {
        // Filtra los productoData que no son recomendaciones
        productoData = productoData.filter { !it.esRecomendacion }
        notifyDataSetChanged()
    }

    // Método para agregar nuevas recomendaciones
    fun agregarRecomendaciones(recomendaciones: List<ProductoData>) {
        productoData = productoData + recomendaciones.map { it.copy(esRecomendacion = true) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productoData[position]
        holder.nombreProducto.text = producto.nombreProducto
        holder.descripcionProducto.text = producto.descripcionProducto
        holder.precioProducto.text = "Precio: ${producto.precioProducto}"


        // Construye la URL de la imagen utilizando el ID del producto
        val imageUrl = "http://192.168.1.70:5000/obtener_imagen/${producto.idProducto}"

        // Utiliza Picasso para cargar imágenes desde la URL
        Picasso.get().load(imageUrl).into(holder.imagenProducto)

        //nuevo solis favorito
        holder.btnFavorito.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (!esProductoEnFavoritos(producto, productoData)) {
                    // Si no está en favoritos, agrégalo
                    producto.esFavorito = true
                    enviarProductoFavorito(producto, holder.itemView.context)
                    Toast.makeText(
                        holder.itemView.context,
                        "ProductoData agregado a favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Si ya está en favoritos, muestra un mensaje
                    Toast.makeText(
                        holder.itemView.context,
                        "ProductoData ya está en favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Configura un clic en el botón para enviar el producto al servidor
        holder.btnCraito.setOnClickListener {
            val username = Login.getUsernameFromSharedPreferences(holder.itemView.context)
            Log.d("Username", "Valor de username: $username")
            if (username.isEmpty()) {
                Toast.makeText(holder.itemView.context,"Inicia sesión para agregar productoData al carrito",Toast.LENGTH_SHORT).show()
            } else {
                producto.nombreUsuario=username
                if (producto.cantidadEnCarrito == 0) {
                    producto.cantidadEnCarrito = 1
                }
                notifyDataSetChanged()
                CoroutineScope(Dispatchers.Main).launch {
                    enviarProductoAlServidor(producto)
                    Toast.makeText(it.context, "ProductoData agregado al carrito", Toast.LENGTH_SHORT).show()
                    producto.cantidadEnCarrito = 0
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return productoData.size
    }
}