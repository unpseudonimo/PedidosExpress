package com.example.pedidosexpress.views.consumidor.homeCosumidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.pedidosexpress.R

class ProductsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products, container, false)

        // Obtén la referencia al ListView utilizando la ID "lista"
        val productListView: ListView = view.findViewById(R.id.lista)

        // Crea un array de ejemplo de productos (utilizando el método generateSampleProducts)
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

        val productNames = arrayOf("Coca Cola 600ml", "Pepsi 500ml", "Sprite 750ml", "Fanta 330ml", "Dr. Pepper 355ml", "Mountain Dew 500ml", "7UP 330ml", "Mirinda 250ml", "Root Beer 355ml", "Sunkist 473ml")

        val productPrices = arrayOf("$18.00", "$20.00", "$15.00", "$12.50", "$22.50", "$19.00", "$14.00", "$11.00", "$25.00", "$17.50")

        val productImages = intArrayOf(
            R.drawable.logo_1,
            R.drawable.logo_1,
            R.drawable.logo_1,
            R.drawable.logo_1,
            R.drawable.logo_1,
            R.drawable.logo_1,
            R.drawable.logo_1,
            R.drawable.logo_1,
            R.drawable.logo_1,
            R.drawable.logo_1
        )

        for (i in productNames.indices) {
            val product = Product(
                name = productNames[i],
                price = productPrices[i],
                imageResId = productImages[i]
            )
            productList.add(product)
        }

        return productList
    }

    // Clase de datos para representar un producto
    data class Product(val name: String, val price: String, val imageResId: Int)

}
