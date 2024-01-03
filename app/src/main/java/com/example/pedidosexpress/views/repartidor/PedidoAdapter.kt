package com.example.pedidosexpress.views.repartidor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.Pedido

// Adaptador para la lista de pedidos en el RecyclerView
class PedidoAdapter(private val listaPedidos: MutableList<Pedido>) :
    RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    // Clase interna para contener las vistas de cada elemento del RecyclerView
    class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPedidoId: TextView = itemView.findViewById(R.id.tvPedidoId)
        val tvNombreProducto: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
    }

    // Método llamado cuando se necesita crear una nueva vista de elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        // Inflar el diseño del elemento de pedido
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        // Devolver una instancia del ViewHolder que contiene las vistas
        return PedidoViewHolder(itemView)
    }

    // Método llamado para mostrar los datos en una vista de elemento
    // Dentro del método onBindViewHolder en PedidoAdapter
    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        // Obtener el pedido en la posición dada
        val pedido = listaPedidos.getOrNull(position)

        // Verificar si el pedido no es nulo antes de intentar mostrarlo
        pedido?.let {
            // Establecer los valores de las vistas con los datos del pedido
            holder.tvPedidoId.text = it.pedidoId ?: "ID no disponible"
            holder.tvNombreProducto.text = it.nombreProducto ?: "Producto no disponible"
            holder.tvPrecio.text = "Precio: ${it.precio?.toString() ?: "No disponible"}"
            holder.tvEstado.text = "Estado: ${it.estado ?: "No disponible"}"
        }
    }


    // Método llamado para obtener la cantidad de elementos en la lista
    override fun getItemCount(): Int {
        return listaPedidos.size
    }

    // Método para actualizar la lista de pedidos con una nueva lista
    fun actualizarPedidos(nuevaLista: List<Pedido>) {
        Log.d("MiApp", "Datos del servidor: $nuevaLista")
        if (nuevaLista.isNotEmpty()) {
            // Limpiar la lista actual y agregar todos los elementos de la nueva lista
            listaPedidos.clear()
            listaPedidos.addAll(nuevaLista)
            // Notificar al adaptador que los datos han cambiado
            notifyDataSetChanged()
        }
    }

    // Método para agregar un nuevo pedido a la lista
    fun agregarPedido(pedido: Pedido?) {
        pedido?.let {
            // Agregar el pedido a la lista
            listaPedidos.add(it)
            // Notificar al adaptador que se ha insertado un nuevo elemento
            notifyItemInserted(listaPedidos.size - 1)
        }
    }

    // Método para borrar todos los pedidos de la lista
    fun borrarTodosPedidos() {
        // Limpiar la lista de pedidos
        listaPedidos.clear()
        // Notificar al adaptador que los datos han cambiado
        notifyDataSetChanged()
    }
}
