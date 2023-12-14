package com.example.pedidosexpress.views.repartidor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LoginRFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login_r, container, false)

        val btnBack: FloatingActionButton = view.findViewById(R.id.btnback)

        // Configura un OnClickListener para el botón de retroceso
        btnBack.setOnClickListener {val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}