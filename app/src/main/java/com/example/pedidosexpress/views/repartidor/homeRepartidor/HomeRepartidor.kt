package com.example.pedidosexpress.views.repartidor.homeRepartidor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.consumidor.BottomNavigationHandler

class HomeRepartidor : AppCompatActivity() {

    private lateinit var bottomNavigationHandler: BottomNavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homerepartidor) // Establece el layout o interfaz de la actividad

        bottomNavigationHandler = BottomNavigationHandler(this)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MapaRFragment>(R.id.FragmentContainer)
        }

        loadFragment(HomeRFragment())
    }


    private fun loadFragment(fragment: HomeRFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.home_item, fragment)
            .commit()
    }
}