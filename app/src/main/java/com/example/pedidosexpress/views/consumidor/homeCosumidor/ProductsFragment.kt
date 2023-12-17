package com.example.pedidosexpress.views.consumidor.homeCosumidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.pedidosexpress.R

class ProductsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products, container, false)

        // Obtén la referencia al ListView
        val productListView: ListView = view.findViewById(R.id.lista)

        // Crea un array de ejemplo de productos (puedes reemplazar esto con tu lógica de obtención de datos)
        val productList = generateSampleProducts()

        // Crea un adaptador personalizado
        val productAdapter =
            ProductAdapter(requireContext(), R.layout.lista_prodcutos, productList)

        // Establece el adaptador en el ListView
        productListView.adapter = productAdapter

        return view
    }

    // Método para generar productos de ejemplo (puedes reemplazarlo con tu propia lógica)
    private fun generateSampleProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        productList.add(Product("Producto", "$10", R.drawable.logo_1))

        // Agrega más productos según sea necesario
        return productList
    }

    // Clase de datos para representar un producto
    data class Product(val name: String, val price: String, val imageResId: Int)

}
