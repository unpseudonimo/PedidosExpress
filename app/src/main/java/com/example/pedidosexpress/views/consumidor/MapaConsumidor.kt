package com.example.pedidosexpress.views.consumidor

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.Manifest
import com.example.pedidosexpress.R
import com.example.pedidosexpress.views.main.login
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.math.RoundingMode
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.OnMapReadyCallback
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class MapaConsumidor : AppCompatActivity(),
    OnMapReadyCallback {
    private lateinit var recyclerView: RecyclerView
    private lateinit var TotalTextView: TextView
    private lateinit var map: GoogleMap
    private lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa_consumidor)
//logica del mapa
        // Inicializar el fragmento de mapa
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

//listar los productos comprados y repartidor
        //lista de productos:
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Referencia al TextView del total
        TotalTextView = findViewById(R.id.Total)
        // Obtener el username y asignarlo a la propiedad de la clase
        username = login.getUsernameFromSharedPreferences(this@MapaConsumidor)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.80:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.procesarCompra(username)

        call.enqueue(object : Callback<List<PagosData>> {
            override fun onResponse(call: Call<List<PagosData>>, response: Response<List<PagosData>>) {
                if (response.isSuccessful) {
                    val pagosDataList  = response.body()
                    if (pagosDataList  != null) {
                        val userId = login.getUsernameFromSharedPreferences(applicationContext) // Asegúrate de tener acceso al contexto aquí
                        val adapter = PagosConsumidorActivity(pagosDataList, userId)
                        recyclerView.adapter = adapter
                    }
                } else {
                    Log.e("MainActivity", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<PagosData>>, t: Throwable) {
                Log.e("MainActivity", "Error: ${t.message}")
            }
        })

    }
    //funciones del mapa de googel
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Habilitar la capa de mi ubicación en el mapa
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true

            // Obtener la última ubicación conocida del usuario
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Mover la cámara a la ubicación actual del usuario
                    location?.let {
                        val latLng = LatLng(it.latitude, it.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        // Enviar datos al servidor Flask
                        enviarDatosAlServidor(username, it)
                    }
                }
        } else {
            // Solicitar permisos de ubicación si no están otorgados
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
    // Obtener la ubicación y enviar datos al servidor
    fun enviarDatosAlServidor(nombre: String, ubicacion: Location) {

        val url = "http://192.168.1.80:5000/UbicacionEntrega"

        val jsonObject = JSONObject()
        jsonObject.put("nombre", nombre)
        jsonObject.put("latitud", ubicacion.latitude)
        jsonObject.put("longitud", ubicacion.longitude)

        val jsonString = jsonObject.toString()

        Thread {
            try {
                val url = URL(url)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val os = connection.outputStream
                os.write(jsonString.toByteArray(Charset.forName("UTF-8")))
                os.close()

                val responseCode = connection.responseCode
                // Manejar la respuesta del servidor según sea necesario

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

}