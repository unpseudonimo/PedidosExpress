package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pedidosexpress.R
import com.example.pedidosexpress.databinding.ActivityInvitarAmigosBinding

class InvitarAmigosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInvitarAmigosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvitarAmigosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInvitarAmigos.setOnClickListener {
            abrirActividadCompartir()
        }
    }

    private fun abrirActividadCompartir() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "¡Únete a nuestra aplicación! Descárgala desde: [Enlace de tu aplicación]")
        startActivity(Intent.createChooser(intent, "Invitar Amigos"))
    }
}
