package com.example.pedidosexpress.views.consumidor

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject

class CarritoAdapter(private val CarritoAdap: MutableList<CarritoData>, private val listener: OnCantidadChangeListener) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenProducto: ImageView = itemView.findViewById(R.id.imagenProducto)
        val nombreProducto: TextView = itemView.findViewById(R.id.nombreProducto)
        val descripcionProducto: TextView = itemView.findViewById(R.id.descripcionProducto)
        val precioProducto: TextView = itemView.findViewById(R.id.precioProducto)
        val mostrarCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        val CantidadMenos: TextView = itemView.findViewById(R.id.btnMenos)
        val CantidadMas: TextView = itemView.findViewById(R.id.btnMas)

    }
    interface OnCantidadChangeListener {
        fun onCantidadChanged(producto: CarritoData)

    }
    fun getProductos(): List<CarritoData> {
        return CarritoAdap
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: CarritoAdapter.ViewHolder, position: Int) {
        Log.d("CarritoAdapter", "Procesando producto en posición $position")
        val producto = CarritoAdap[position]
        holder.nombreProducto.text = producto.nombreProducto
        holder.descripcionProducto.text = producto.descripcionProducto
        holder.precioProducto.text = "Precio: $ ${producto.precioProducto}"
        holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()


        // Construye la URL de la imagen utilizando el ID del producto
        val imageUrl = "http://192.168.1.80:5000/obtener_imagen/${producto.idProducto}"

        // Utiliza Picasso para cargar imágenes desde la URL
        Picasso.get().load(imageUrl).into(holder.imagenProducto)

        //modificar cantidades y eliminar producto del carrito
        var respuestaServidor: String = ""
        // Configura los clics en los botones para actualizar la cantidad
        holder.CantidadMas.setOnClickListener {
            producto.cantidadEnCarrito++
            // Guardar la cantidad actualizada en una variable
            val cantidadActualizada = producto.cantidadEnCarrito
            holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()
            var estado:String="Aumento"

            listener.onCantidadChanged(producto)

            // También puedes enviar la actualización al servidor aquí si es necesario
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    respuestaServidor = enviarProductoAlServidor(cantidadActualizada,estado,producto.idProducto)
                }catch (e: Exception) {
                    Toast.makeText(it.context, respuestaServidor, Toast.LENGTH_SHORT).show()
                }
            }
        }

        holder.CantidadMenos.setOnClickListener {
            var cantidadActualizada = 0
            var estado: String = "Decremento"
            var respuestaServidor: String = ""
            if (producto.cantidadEnCarrito > 1) {
                producto.cantidadEnCarrito--
                cantidadActualizada = producto.cantidadEnCarrito
                holder.mostrarCantidad.text = producto.cantidadEnCarrito.toString()

                listener.onCantidadChanged(producto)
                // También puedes enviar la actualización al servidor aquí si es necesario
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        respuestaServidor = enviarProductoAlServidor(cantidadActualizada,estado,producto.idProducto)
                    }catch (e: Exception) {
                        Toast.makeText(it.context, respuestaServidor, Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                // Enviar la solicitud al servidor antes de mostrar el diálogo
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        respuestaServidor = enviarProductoAlServidor(cantidadActualizada, estado, producto.idProducto)
                    } catch (e: Exception) {
                        Toast.makeText(it.context, respuestaServidor, Toast.LENGTH_SHORT).show()
                    }
                    // Mostrar un cuadro de diálogo para confirmar la eliminación
                    mostrarDialogoEliminarProducto(holder.itemView, producto)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return CarritoAdap.size
    }
    //cambiar cantidad del carrito servidor
    private suspend fun enviarProductoAlServidor(cantidad: Int,estado: String,idProducto: Int): String {
        val url = "http://192.168.1.80:5000/carritoCantidad"
        val json = Gson().toJson(mapOf("cantidad" to cantidad,"estado" to estado,"idProducto" to idProducto))
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .post(RequestBody.create("application/json".toMediaTypeOrNull(), json))
                .build()

            val response = OkHttpClient().newCall(request).execute()

            // Manejar respuesta exitosa
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                // Manejar el cuerpo de la respuesta (si es necesario)
                println("Respuesta exitosa: $responseBody")
                // Intentar analizar el JSON y extraer el mensaje
                try {
                    val jsonResponse = JSONObject(responseBody)
                    return@withContext jsonResponse.optString("mensaje", "Respuesta del servidor sin mensaje")
                } catch (e: JSONException) {
                    return@withContext "Error al analizar la respuesta del servidor"
                }
            } else {
                // Manejar error en la solicitud
                val errorBody = response.body?.string()
                println("Error en la solicitud: $errorBody")
                return@withContext "Error en la solicitud al servidor"
            }
        }
    }
    // Método para eliminar el producto del carrito y actualizar la vista
    fun eliminarProducto(producto: CarritoData) {
        // Lógica para eliminar el producto y actualizar la vista (puedes ajustar según tu implementación)
        val posicion = CarritoAdap.indexOf(producto)
        if (posicion != -1) {
            CarritoAdap.removeAt(posicion)
            notifyItemRemoved(posicion)
        }
    }
    // Método para mostrar un cuadro de diálogo de confirmación para eliminar el producto
    private fun mostrarDialogoEliminarProducto(itemView: View, producto: CarritoData) {
        val builder = MaterialAlertDialogBuilder(itemView.context)
        builder.setTitle("Eliminar Producto")
        builder.setMessage("¿Seguro que deseas eliminar este producto del carrito?")

        builder.setPositiveButton("Aceptar") { _, _ ->
            // Eliminar el producto del carrito y actualizar la vista
            eliminarProducto(producto)

            // También puedes realizar alguna consulta al servidor aquí, si es necesario
            // ...

            // Notificar al listener sobre el cambio en la cantidad (si es necesario)
            listener.onCantidadChanged(producto)
        }

        builder.setNegativeButton("Cancelar") { _, _ ->
            // Restaurar la cantidad del producto si el usuario cancela
            producto.cantidadEnCarrito++
            (itemView as? ViewHolder)?.mostrarCantidad?.text = producto.cantidadEnCarrito.toString()
        }

        val dialog = builder.create()
        dialog.show()
    }
}