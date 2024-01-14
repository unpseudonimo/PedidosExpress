package com.example.pedidosexpress.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.model.PedidoEntregado

class PedidoEntregadoAdapter : RecyclerView.Adapter<PedidoEntregadoAdapter.ViewHolder>() {

    private var pedidos: List<PedidoEntregado> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pedidos_entregados, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pedido = pedidos[position]
        Log.d("PedidoEntregadoAdapter", "Pedido en posición $position: $pedido")

        // Configura tus vistas según la información del pedido
        holder.tvDireccion.text = "Dirección: ${pedido.direccion}"
        holder.tvTotal.text = "Total: ${pedido.total}"
        holder.tvProductos.text = "Productos: ${pedido.productos.joinToString { it.nombre_producto }}"
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }

    fun setPedidos(pedidos: List<PedidoEntregado>) {
        this.pedidos = pedidos
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDireccion: TextView = itemView.findViewById(R.id.tvDireccionConsumidor)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTotalPedido)
        val tvProductos: TextView = itemView.findViewById(R.id.tvProductos)
        // Añade otras vistas según tus necesidades
    }
}
