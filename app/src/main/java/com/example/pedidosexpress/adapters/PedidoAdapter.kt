package com.example.pedidosexpress.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.databinding.ItemPedidoBinding
import com.example.pedidosexpress.interfaces.IOnDetallePedidoClickListener
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
        fun bind(pedidoAsignado: PedidoAsignado) {
            binding.apply {
                tvDireccionConsumidor.text = "Direccion: ${pedidoAsignado.direccion ?: "Dirección no disponible"}"
                tvTotalPedido.text = "Total: ${pedidoAsignado.total.toString()}"

                val productosStr = StringBuilder()
                pedidoAsignado.productos?.forEach { producto ->
                    productosStr.append("${producto.nombre_producto} (${producto.Cantidad_producto} unidades)\n")
                }
                tvProductosPedido.text = "Productos: ${productosStr.toString().trim()}"


                btnAceptarEntrega.setOnClickListener {
                    val context = it.context
                    val nombreRepartidor = Login.getUsernameFromSharedPreferences(context)

                    // Llamar a la API para confirmar la entrega
                    confirmarEntrega(nombreRepartidor)

                    // Iniciar la actividad DetallePedido después de confirmar la entrega
                    val intent = Intent(context, DetallePedido::class.java)
                    val productosStr = StringBuilder()
                    // Agregar datos adicionales al Intent
                    intent.putExtra("Direccion", pedidoAsignado.direccion)
                    intent.putExtra("Total", pedidoAsignado.total)

                    pedidoAsignado.productos?.forEach { producto ->
                        productosStr.append("${producto.nombre_producto} (${producto.Cantidad_producto} unidades)\n")
                    }
                    intent.putExtra("Productos:", productosStr.toString().trim())

                    // Iniciar la actividad DetallePedido
                    context.startActivity(intent)
                }


            }
        }

        // Función corregida
        private fun confirmarEntrega(nombreRepartidor: String) {
            val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.buildApiUrl(""))
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)
            val call: Call<Void> = apiService.confirmarEntrega(nombreRepartidor)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    // Lógica para manejar la respuesta exitosa si es necesario

                    // Mostrar un Toast de "Pedido confirmado"
                    Toast.makeText(itemView.context, "Pedido confirmado", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Lógica para manejar el error si es necesario
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
