package com.example.pedidosexpress.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.databinding.ItemPedidoBinding
import com.example.pedidosexpress.interfaces.IOnDetallePedidoClickListener
import com.example.pedidosexpress.model.CodigoEntregaRequest
import com.example.pedidosexpress.model.PedidoAsignado
import com.example.pedidosexpress.views.consumidor.ApiService
import com.example.pedidosexpress.views.consumidor.AppConfig
import com.example.pedidosexpress.views.main.Login
import com.example.pedidosexpress.views.repartidor.DetallePedido
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PedidoAdapter(
    private val listaPedidoAsignado: MutableList<PedidoAsignado>,
    private val onDetallePedidoClickListener: IOnDetallePedidoClickListener
) : RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    class PedidoViewHolder(private val binding: ItemPedidoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var isPedidoConfirmado: Boolean = false

        fun bind(pedidoAsignado: PedidoAsignado) {
            binding.apply {
                tvDireccionConsumidor.text = "Direccion: ${pedidoAsignado.direccion ?: "Dirección no disponible"}"
                tvTotalPedido.text = "Total: ${pedidoAsignado.total}"

                val productosStr = StringBuilder()
                pedidoAsignado.productos?.forEach { producto ->
                    productosStr.append("${producto.nombre_producto} (${producto.Cantidad_producto} unidades)\n")
                }
                tvProductosPedido.text = "Productos: ${productosStr.toString().trim()}"

                val estadoPedido = "En camino"
                // Verifica el estado del pedido y ajusta el botón en consecuencia
                if (pedidoAsignado.estado_pedido == estadoPedido) {
                    Log.d("Estado pedido asignado", pedidoAsignado.estado_pedido)
                    btnAceptarEntrega.text = "Entregar pedido"
                    isPedidoConfirmado = true
                } else {
                    btnAceptarEntrega.text = "Aceptar entrega"
                    isPedidoConfirmado = false
                }

                btnAceptarEntrega.setOnClickListener {
                    val context = it.context
                    val nombreRepartidor = Login.getUsernameFromSharedPreferences(context)

                    if (isPedidoConfirmado) {
                        // Si el pedido ya está confirmado, solicitar código de entrega
                        solicitarCodigoEntrega(context) { codigoIngresado ->
                            entregarPedido(nombreRepartidor, codigoIngresado) { exitosa ->
                                if (exitosa) {
                                    // Lógica después de una entrega exitosa (puedes personalizar esto)
                                    val intent = Intent(context, DetallePedido::class.java)
                                    // ... (código existente)
                                    context.startActivity(intent)
                                } else {
                                    // Mostrar un mensaje de error si la entrega no fue exitosa
                                    Toast.makeText(context, "Error al entregar el pedido", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        // Si el pedido no está confirmado, llamar a la API para confirmar la entrega
                        confirmarEntrega(nombreRepartidor) { exitosa ->
                            if (exitosa) {
                                // Cambiar el texto del botón solo si la confirmación es exitosa
                                btnAceptarEntrega.text = "Entregar pedido"
                                isPedidoConfirmado = true
                            } else {
                                // Mostrar un mensaje de error si la confirmación no es exitosa
                                Toast.makeText(context, "Error al confirmar la entrega", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        private fun confirmarEntrega(nombreRepartidor: String, callback: (Boolean) -> Unit) {
            val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.buildApiUrl(""))
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)
            val call: Call<Void> = apiService.confirmarEntrega(nombreRepartidor)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    // Mostrar un Toast de "Pedido confirmado"
                    Toast.makeText(itemView.context, "Pedido confirmado", Toast.LENGTH_SHORT).show()
                    callback(true)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Lógica para manejar el error si es necesario
                    callback(false)
                }
            })
        }

        private fun solicitarCodigoEntrega(context: Context, callback: (String) -> Unit) {
            val builder = AlertDialog.Builder(context)
            val editText = EditText(context)
            builder.setView(editText)
            builder.setTitle("Ingrese el código de entrega")
            builder.setPositiveButton("Aceptar") { _, _ ->
                val codigoIngresado = editText.text.toString()
                callback(codigoIngresado)
            }
            builder.setNegativeButton("Cancelar") { _, _ ->
                // Puedes manejar la cancelación si es necesario
            }
            builder.show()
        }

        private fun entregarPedido(nombreRepartidor: String, codigoEntrega: String, callback: (Boolean) -> Unit) {
            // Crear una instancia de Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.buildApiUrl("")) // Reemplaza con la URL de tu servidor
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Crear una instancia de la interfaz ApiService
            val apiService = retrofit.create(ApiService::class.java)

            // Crear una instancia de CodigoEntregaRequest con el código de entrega
            val codigoEntregaRequest = CodigoEntregaRequest(codigoEntrega)

            // Hacer la llamada a la API
            val call: Call<Void> = apiService.entregarPedido(nombreRepartidor, codigoEntregaRequest)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Éxito al entregar el pedido
                        callback(true)
                    } else {
                        // Manejar la respuesta de error
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Manejar el fallo en la comunicación
                    callback(false)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val binding =
            ItemPedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PedidoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedidoAsignado = listaPedidoAsignado.getOrNull(position)
        pedidoAsignado?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = listaPedidoAsignado.size

    fun actualizarPedidos(nuevaLista: List<PedidoAsignado>?) {
        nuevaLista?.let {
            listaPedidoAsignado.clear()
            listaPedidoAsignado.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun agregarPedido(pedidoAsignado: PedidoAsignado?) {
        pedidoAsignado?.let {
            listaPedidoAsignado.add(it)
            notifyItemInserted(listaPedidoAsignado.size - 1)
        }
    }

    fun borrarTodosPedidos() {
        listaPedidoAsignado.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): PedidoAsignado = listaPedidoAsignado[position]
}
