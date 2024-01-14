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
import android.os.Handler
import android.widget.Button
import com.example.pedidosexpress.R
import com.example.pedidosexpress.model.PagosData
import com.example.pedidosexpress.views.main.Login
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.RoundingMode
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class MapaConsumidor : AppCompatActivity(),
    OnMapReadyCallback {
    private lateinit var recyclerView: RecyclerView
    private lateinit var TotalTextView: TextView
    private lateinit var EstadoPedido: TextView
    private lateinit var EstadoRepatidor: TextView
    private lateinit var direccion: TextView
    private lateinit var telefono: TextView
    private lateinit var map: GoogleMap
    private var cameraMoved = false
    private val handler = Handler()
    private lateinit var username: String
    private val buscarRepartidorRunnable = object : Runnable {
        override fun run() {
            // Llamada automática a la función para buscar la ubicación del repartidor
            if (::map.isInitialized) {
                enviarDatosBuscarUbicacion(username)
            }

            // Programar la próxima ejecución después de un intervalo de tiempo (por ejemplo, 10 segundos)
            handler.postDelayed(this, 10000) // 10000 milisegundos = 10 segundos
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa_consumidor)
//logica del mapa
        // Inicializar el fragmento de mapa
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        // Llamada inicial para buscar la ubicación del repartidor
        handler.post(buscarRepartidorRunnable)

// Lista de productos comprados y repartidor
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Referencia al TextView del total
        TotalTextView = findViewById(R.id.Total)
        EstadoPedido=findViewById(R.id.EstadoPedido)
        EstadoRepatidor=findViewById(R.id.EstadoRepartidor)


        // Obtener el username y asignarlo a la propiedad de la clase
        username = Login.getUsernameFromSharedPreferences(this@MapaConsumidor)




        val retrofit = Retrofit.Builder()
            .baseUrl(AppConfig.buildApiUrl(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.procesarCompra(username)

        call.enqueue(object : Callback<List<PagosData>> {
            override fun onResponse(call: Call<List<PagosData>>, response: Response<List<PagosData>>) {
                if (response.isSuccessful) {
                    val pagosDataList = response.body()
                    if (pagosDataList != null) {
                        Log.d("MainActivity", "Response: $pagosDataList")

                        // Calcular y mostrar el total de la compra
                        var totalCompra = 0.0
                        for (pagosData in pagosDataList) {
                            totalCompra += pagosData.totalCompra
                        }
                        TotalTextView.text ="$totalCompra"
                        // Mostrar datos adicionales
                        val primerPagosData = pagosDataList.firstOrNull()
                        if (primerPagosData != null) {
                            // Mostrar estado del pedido
                            EstadoPedido.text = "Estado del pedido: ${primerPagosData.estadoPedido ?: ""}"

                            // Mostrar estado del repartidor
                            EstadoRepatidor.text = "Estado del repartidor: ${primerPagosData.estadoRepartidor ?: ""}"

                        }
                        // Obtener la lista de ProductoPagado
                        val productoPagadoList = pagosDataList.flatMap { it.productoPagado.orEmpty() }
                        // Configurar el adaptador con la nueva estructura de datos
                        val adapter = PagosConsumidorActivity(productoPagadoList, username)
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

                        // Llamada inicial para buscar la ubicación del repartidor
                        handler.post(buscarRepartidorRunnable)
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

        val url = AppConfig.buildApiUrl("UbicacionEntrega")

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

    fun enviarDatosBuscarUbicacion(nombre: String) {

        val url = AppConfig.buildApiUrl("obtenerUbicacion")

        val jsonObject = JSONObject()
        jsonObject.put("nombre", nombre)

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

                // Leer la respuesta del servidor
                val inputStream = if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }

                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val responseStringBuilder = StringBuilder()

                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    responseStringBuilder.append(line).append("\n")
                }

                bufferedReader.close()

                // Procesar la respuesta del servidor
                val jsonResponse = JSONObject(responseStringBuilder.toString())
                val mensaje = jsonResponse.getString("mensaje")

                if (mensaje == "Usuario y ubicación encontrados") {
                    val nombre = jsonResponse.getString("nombre")
                    val ubicacion = jsonResponse.getJSONObject("ubicacion")
                    val latitud = ubicacion.getDouble("latitud")
                    val longitud = ubicacion.getDouble("longitud")

                    // Guardar la ubicación del usuario
                    val ubicacionUsuario = Location("Usuario")
                    ubicacionUsuario.latitude = latitud
                    ubicacionUsuario.longitude = longitud

                    // Actualizar el mapa con las ubicaciones del usuario y del repartidor
                    runOnUiThread {
                        val latLngUsuario = LatLng(ubicacionUsuario.latitude, ubicacionUsuario.longitude)
                        val latLngRepartidor = LatLng(latitud, longitud)

                        // Limpiar marcadores existentes
                        map.clear()

                        // Agregar marcador para la ubicación del repartidor
                        map.addMarker(MarkerOptions().position(latLngRepartidor).title(nombre).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))

                        // Mover la cámara al punto medio entre las dos ubicaciones solo si no se ha movido antes
                        if (!cameraMoved) {
                            val boundsBuilder = LatLngBounds.builder().include(latLngUsuario).include(latLngRepartidor)
                            val bounds = boundsBuilder.build()
                            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50)
                            map.moveCamera(cameraUpdate)

                            // Marcar que la cámara se ha movido
                            cameraMoved = true
                        }
                    }
                } else {
                    // ... (mismo código para manejar el caso en que el usuario o la ubicación no fueron encontrados)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

}