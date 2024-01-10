package com.example.pedidosexpress.views.consumidor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.Login

class WebViewActivity : AppCompatActivity() {
    private lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        username = Login.getUsernameFromSharedPreferences(this@WebViewActivity)

        val url = intent.getStringExtra("url")
        val pagoExitosoUrl = intent.getStringExtra("pagoExitosoUrl")
        val urlConParametros = "$url?username=$username"
        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                Log.d("WebViewActivity", "onPageFinished: $url")

                // Verificar si la URL base coincide con la de pago exitoso
                if (url != null && url.startsWith(AppConfig.buildApiUrl("pago_exitoso"))) {
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
        webView.loadUrl(urlConParametros)
    }
}