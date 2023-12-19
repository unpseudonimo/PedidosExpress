package com.example.pedidosexpress.views.consumidor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.pedidosexpress.R

class ProductAdapter(context: Context, resource: Int, objects: List<ProductsFragment.Product>) :
    ArrayAdapter<ProductsFragment.Product>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.lista_prodcutos, parent, false)

        // Obtén el producto en la posición actual
        val product = getItem(position)

        // Configura los elementos de la vista con los datos del producto
        val productName: TextView = itemView.findViewById(R.id.nombre_Producto)
        val productPrice: TextView = itemView.findViewById(R.id.precioProducto)
        val productImage: ImageView = itemView.findViewById(R.id.imageProducto)
        val addToCartButton: Button = itemView.findViewById(R.id.btnagregar)

        productName.text = product?.name
        productPrice.text = product?.price
        productImage.setImageResource(product?.imageResId ?: 0)

        // Configura la lógica del botón (agregar al carrito) según tus necesidades

        return itemView
    }
}
