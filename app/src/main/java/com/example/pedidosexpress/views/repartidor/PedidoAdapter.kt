package com.example.pedidosexpress.views.repartidor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.common.Pedido

// Interfaz para manejar eventos de clic en el botón "Ver Detalle"
interface OnDetallePedidoClickListener {
    fun onDetallePedidoClick(view: View)
}

class PedidoAdapter(val listaPedidos: MutableList<Pedido>) :
    RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    private var onDetallePedidoClickListener: OnDetallePedidoClickListener? = null

    // Método para establecer el listener
    fun setOnDetallePedidoClickListener(listener: OnDetallePedidoClickListener) {
        this.onDetallePedidoClickListener = listener
    }

    class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPedidoId: TextView = itemView.findViewById(R.id.tvPedidoId)
        val tvNombreCliente: TextView = itemView.findViewById(R.id.tvNombreCliente)
        val tvNombreRepartidor: TextView = itemView.findViewById(R.id.tvNombreRepartidor)
        val tvProductos: TextView = itemView.findViewById(R.id.tvProductos)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTotal)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        val btnVerDetalle: Button = itemView.findViewById(R.id.btnVerDetalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        return PedidoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = listaPedidos.getOrNull(position)

        pedido?.let {
            val orderId = when (val id = it._id) {
                is Map<*, *> -> id["_id"]?.toString() ?: "ID no disponible"
                is String -> id
                else -> "ID no disponible"
            }
            holder.tvPedidoId.text = "Pedido ID: $orderId"
            holder.tvNombreCliente.text = it.nombre_cliente ?: "Cliente no disponible"
            holder.tvNombreRepartidor.text = it.nombre_repartidor ?: "Repartidor no disponible"
            holder.tvTotal.text = "Total: ${it.total.toString()}"
            holder.tvEstado.text = "Estado: ${it.estado ?: "No disponible"}"

            // Construir la cadena de productos
            val productosStr = StringBuilder()
            for (producto in it.productos) {
                productosStr.append("${producto.nombreProducto} (${producto.cantidadEnCarrito} unidades)\n")
            }
            holder.tvProductos.text = productosStr.toString().trim()

            // Configurar el clic del botón para mostrar el detalle del pedido
            holder.btnVerDetalle.setOnClickListener {
                onDetallePedidoClickListener?.onDetallePedidoClick(it)

                // Obtener el contexto del botón (que debe ser un contexto de actividad)
                val context = holder.itemView.context

                // Crear un Intent para iniciar la actividad DetallePedido
                val intent = Intent(context, DetallePedido::class.java)

                // Pasar el ID del pedido a la actividad DetallePedido
                intent.putExtra("pedido_id", pedido._id.toString())

                // Iniciar la actividad DetallePedido
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listaPedidos.size
    }

    fun actualizarPedidos(nuevaLista: List<Pedido>) {
        if (nuevaLista.isNotEmpty()) {
            listaPedidos.clear()
            listaPedidos.addAll(nuevaLista)
            notifyDataSetChanged()
        }
    }

    fun agregarPedido(pedido: Pedido?) {
        pedido?.let {
            listaPedidos.add(it)
            notifyItemInserted(listaPedidos.size - 1)
        }
    }

    fun borrarTodosPedidos() {
        listaPedidos.clear()
        notifyDataSetChanged()
    }
}
