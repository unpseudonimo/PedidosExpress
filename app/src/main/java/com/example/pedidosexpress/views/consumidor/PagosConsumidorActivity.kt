package com.example.pedidosexpress.views.consumidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.model.ProductoPagado
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class PagosConsumidorActivity(private val productoPagadoList: List<ProductoPagado>, private val userId: String) : RecyclerView.Adapter<PagosConsumidorActivity.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenProducto: ImageView = itemView.findViewById(R.id.imagenProducto)
        val nombreProducto: TextView = itemView.findViewById(R.id.nombreProducto)
        val precioProducto: TextView = itemView.findViewById(R.id.precioProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_pagos_consumidor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productoPagado = productoPagadoList[position]

        val nombreProducto = productoPagado.nombreProducto ?: ""
        val precioProducto = productoPagado.precioProducto

        // Configurar los datos en el ViewHolder para cada producto
        holder.nombreProducto.text = nombreProducto
        holder.precioProducto.text = "Precio: $precioProducto"

        // Construir la URL de la imagen utilizando el ID del producto
        val imageUrl = AppConfig.buildApiUrl("obtener_imagen/${productoPagado.idProducto}")

        // Utilizar Picasso para cargar imágenes desde la URL
        Picasso.get().load(imageUrl).into(holder.imagenProducto)
    }

    override fun getItemCount(): Int {
        return productoPagadoList.size
    }
}