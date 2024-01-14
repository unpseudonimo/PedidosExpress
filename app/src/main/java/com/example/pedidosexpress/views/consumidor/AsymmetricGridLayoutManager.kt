package com.example.pedidosexpress.views.consumidor

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class AsymmetricGridLayoutManager(
    context: Context?,
    spanCount: Int,
    private val columnWidths: IntArray
) : GridLayoutManager(context, spanCount) {

    override fun getSpanSizeLookup(): SpanSizeLookup {
        return object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Devuelve el ancho de columna correspondiente al índice de posición
                return columnWidths[position % columnWidths.size]
            }
        }
    }
}