package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.pedidosexpress.R

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = intent.getStringExtra("url")
        val pagoExitosoUrl = intent.getStringExtra("pagoExitosoUrl")
        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                Log.d("WebViewActivity", "onPageFinished: $url")

                // Verificar si la URL base coincide con la de pago exitoso
                if (url != null && url.startsWith("http://192.168.1.70:5000/pago_exitoso")) {
                    // Realizar acciones después de un pago exitoso
                    // Por ejemplo, cambiar a otra actividad
                    val intent = Intent(this@WebViewActivity, MapaConsumidor::class.java)
                    startActivity(intent)

                    // Finalizar esta actividad (WebViewActivity)
                    finish()
                }
            }
        }

        // Cargar la URL en el WebView
        webView.loadUrl(url.toString())
    }
}