package com.example.pedidosexpress.views.consumidor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R


import com.squareup.picasso.Picasso


class ProductoAdapter(private val productos: List<Producto>) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenProducto: ImageView = itemView.findViewById(R.id.imagenProducto)
        val nombreProducto: TextView = itemView.findViewById(R.id.nombreProducto)
        val descripcionProducto: TextView = itemView.findViewById(R.id.descripcionProducto)
        val precioProducto: TextView = itemView.findViewById(R.id.precioProducto)
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
        val imageUrl = "http://192.168.1.66:5000/obtener_imagen/${producto.idProducto}"

        // Utiliza Picasso para cargar imágenes desde la URL
        Picasso.get().load(imageUrl).into(holder.imagenProducto)
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}
