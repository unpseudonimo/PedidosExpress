package com.example.pedidosexpress.views.consumidor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.model.Producto

class CarritoAdapter(private val context: Context, private val productos: List<Producto>) : BaseAdapter() {

    override fun getCount(): Int = productos.size

    override fun getItem(position: Int): Any = productos[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_producto_carrito, parent, false)

        val imagenProducto: ImageView = view.findViewById(R.id.imagenProducto)
        val nombreProducto: TextView = view.findViewById(R.id.nombreProducto)
        val descripcionProducto: TextView = view.findViewById(R.id.descripcionProducto)
        val precioProducto: TextView = view.findViewById(R.id.precioProducto)

        val producto = productos[position]

        // Asigna los valores del producto a los elementos de la vista
        imagenProducto.setImageResource(producto.imagen)
        nombreProducto.text = producto.nombre
        descripcionProducto.text = producto.descripcionProducto
        precioProducto.text = "$${producto.precio}"

        return view
    }
}
