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
        holder.tvNombreUser.text = "Usuario: ${pedido.Username}"
        holder.tvDireccion.text = "Ubicación de entrega: ${pedido.direccion}"
        holder.tvTelefono.text = "Número de contacto: ${pedido.telefono}"
        //holder.tvTotal.text = "Total: ${pedido.total}"
        holder.tvProductos.text = "Productos: \n ${
            pedido.productos.joinToString {
                "${it.nombre_producto} ${it.Cantidad_producto} unidades"
            }
        }"
        holder.tvEstadoPedido.text = "${pedido.estado_pedido}"
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }

    fun setPedidos(pedidos: List<PedidoEntregado>) {
        this.pedidos = pedidos
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreUser: TextView = itemView.findViewById(R.id.tvNombreConsumidor)
        val tvDireccion: TextView = itemView.findViewById(R.id.tvDireccionConsumidor)
        //val tvTotal: TextView = itemView.findViewById(R.id.tvTotalPedido)
        val tvProductos: TextView = itemView.findViewById(R.id.tvProductos)
        val tvTelefono: TextView = itemView.findViewById(R.id.tvTelefonoConsumidor)
        val tvEstadoPedido: TextView = itemView.findViewById(R.id.tvEstadoPedido)
        // Añade otras vistas según tus necesidades
    }
}
