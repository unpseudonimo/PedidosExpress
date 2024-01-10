package com.example.pedidosexpress.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.databinding.ItemPedidoBinding
import com.example.pedidosexpress.interfaces.IOnDetallePedidoClickListener
import com.example.pedidosexpress.model.PedidoAsignado
import com.example.pedidosexpress.views.repartidor.DetallePedido

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
                    val intent = Intent(context, DetallePedido::class.java)
                    context.startActivity(intent)
                }
            }
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
