package com.example.pedidosexpress.views.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pedidosexpress.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RegistroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registro, container, false)

        val btnBack: FloatingActionButton = view.findViewById(R.id.btnback)

        // Configura un OnClickListener para el botón de retroceso
        btnBack.setOnClickListener {val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}