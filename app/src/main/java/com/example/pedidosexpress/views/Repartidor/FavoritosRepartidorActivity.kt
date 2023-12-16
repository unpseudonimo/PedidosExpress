package com.example.pedidosexpress.views.Repartidor

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosexpress.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoritosRepartidorActivity : AppCompatActivity() {

    private lateinit var tvFavoritosTitle: TextView
    private lateinit var ivFavorito1: ImageView
    private lateinit var ivFavorito2: ImageView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos_repartidor)

        // Inicialización de vistas
        tvFavoritosTitle = findViewById(R.id.tvFavoritosTitle)
        ivFavorito1 = findViewById(R.id.ivFavorito1)
        ivFavorito2 = findViewById(R.id.ivFavorito2)
        bottomNavigation = findViewById(R.id.bottom_navigation)

        // Configuración del título de la sección
        tvFavoritosTitle.text = "Mis Favoritos"

        // Configuración de imágenes de favoritos (puedes cargar tus imágenes aquí)
        ivFavorito1.setImageResource(R.drawable.ic_favorito_1)
        ivFavorito2.setImageResource(R.drawable.ic_favorito_2)

    }
}
