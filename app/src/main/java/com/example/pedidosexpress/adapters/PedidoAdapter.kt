package com.example.pedidosexpress.adapters

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosexpress.R
import com.example.pedidosexpress.databinding.ItemPedidoBinding
import com.example.pedidosexpress.interfaces.IOnDetallePedidoClickListener
import com.example.pedidosexpress.model.CodigoEntregaRequest
import com.example.pedidosexpress.model.PedidoAsignado
import com.example.pedidosexpress.views.consumidor.ApiService
import com.example.pedidosexpress.views.consumidor.AppConfig
import com.example.pedidosexpress.views.main.Login
import com.example.pedidosexpress.views.repartidor.BottomSheetFragment
import com.example.pedidosexpress.views.repartidor.DetallePedido
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset


class PedidoAdapter(
    private val listaPedidoAsignado: MutableList<PedidoAsignado>,
    private val fragmentManager: FragmentManager,
    private val onDetallePedidoClickListener: IOnDetallePedidoClickListener,
) : RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    class PedidoViewHolder(private val binding: ItemPedidoBinding,private val fragmentManager: FragmentManager) :
        RecyclerView.ViewHolder(binding.root), OnMapReadyCallback {
        private var isPedidoConfirmado: Boolean = false
        private lateinit var username: String
        private lateinit var googleMap: GoogleMap
        private var cameraMoved = false
        private val handler = Handler(Looper.getMainLooper())
        private val buscarRepartidorRunnable = object : Runnable {
            override fun run() {
                // Llamada automática a la función para buscar la ubicación del repartidor
                enviarDatosBuscarUbicacion(username)

                // Programar la próxima ejecución después de un intervalo de tiempo (por ejemplo, 10 segundos)
                handler.postDelayed(this, 10000) // 10000 milisegundos = 10 segundos
            }
        }

        fun bind(pedidoAsignado: PedidoAsignado,context: Context) {
            // Obtener el username y asignarlo a la propiedad de la clase
            username = Login.getUsernameFromSharedPreferences(context)
            binding.apply {
                // Inicializar el fragmento de mapa
                val mapView = fragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
                mapView.getMapAsync(this@PedidoViewHolder)
                // Llamada inicial para buscar la ubicación del repartidor
                handler.post(buscarRepartidorRunnable)

                tvDireccionConsumidor.text = "Direccion: ${pedidoAsignado.direccion ?: "Dirección no disponible"}"
                tvTotalPedido.text = "Total: ${pedidoAsignado.total}"

                val productosStr = StringBuilder()
                pedidoAsignado.productos?.forEach { producto ->
                    productosStr.append("${producto.nombre_producto} (${producto.Cantidad_producto} unidades)\n")
                }
                tvProductosPedido.text = "Productos: ${productosStr.toString().trim()}"

                val estadoPedido = "En camino"
                // Verifica el estado del pedido y ajusta el botón en consecuencia
                if (pedidoAsignado.estado_pedido == estadoPedido) {
                    Log.d("Estado pedido asignado", pedidoAsignado.estado_pedido)
                    btnAceptarEntrega.text = "Entregar pedido"
                    isPedidoConfirmado = true
                } else {
                    btnAceptarEntrega.text = "Aceptar entrega"
                    isPedidoConfirmado = false
                }

                btnAceptarEntrega.setOnClickListener {
                    val context = it.context
                    val nombreRepartidor = Login.getUsernameFromSharedPreferences(context)
                    // Crea y muestra el BottomSheetDialogFragment
                    val bottomSheetFragment = BottomSheetFragment()
                    bottomSheetFragment.show(fragmentManager, bottomSheetFragment.tag)



                    if (isPedidoConfirmado) {
                        // Si el pedido ya está confirmado, solicitar código de entrega
                        solicitarCodigoEntrega(context) { codigoIngresado ->
                            entregarPedido(nombreRepartidor, codigoIngresado) { exitosa ->
                                if (exitosa) {
                                    // Lógica después de una entrega exitosa (puedes personalizar esto)
                                    val intent = Intent(context, DetallePedido::class.java)
                                    // ... (código existente)
                                    context.startActivity(intent)
                                } else {
                                    // Mostrar un mensaje de error si la entrega no fue exitosa
                                    Toast.makeText(context, "Error al entregar el pedido", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        // Si el pedido no está confirmado, llamar a la API para confirmar la entrega
                        confirmarEntrega(nombreRepartidor) { exitosa ->
                            if (exitosa) {
                                // Cambiar el texto del botón solo si la confirmación es exitosa
                                btnAceptarEntrega.text = "Entregar pedido"
                                isPedidoConfirmado = true
                            } else {
                                // Mostrar un mensaje de error si la confirmación no es exitosa
                                Toast.makeText(context, "Error al confirmar la entrega", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        private fun confirmarEntrega(nombreRepartidor: String, callback: (Boolean) -> Unit) {
            val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.buildApiUrl(""))
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)
            val call: Call<Void> = apiService.confirmarEntrega(nombreRepartidor)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    // Mostrar un Toast de "Pedido confirmado"
                    Toast.makeText(itemView.context, "Pedido confirmado", Toast.LENGTH_SHORT).show()
                    callback(true)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Lógica para manejar el error si es necesario
                    callback(false)
                }
            })
        }

        private fun solicitarCodigoEntrega(context: Context, callback: (String) -> Unit) {
            val builder = AlertDialog.Builder(context)
            val editText = EditText(context)
            builder.setView(editText)
            builder.setTitle("Ingrese el código de entrega")
            builder.setPositiveButton("Aceptar") { _, _ ->
                val codigoIngresado = editText.text.toString()
                callback(codigoIngresado)
            }
            builder.setNegativeButton("Cancelar") { _, _ ->
                // Puedes manejar la cancelación si es necesario
            }
            builder.show()
        }

        private fun entregarPedido(nombreRepartidor: String, codigoEntrega: String, callback: (Boolean) -> Unit) {
            // Crear una instancia de Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.buildApiUrl("")) // Reemplaza con la URL de tu servidor
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Crear una instancia de la interfaz ApiService
            val apiService = retrofit.create(ApiService::class.java)

            // Crear una instancia de CodigoEntregaRequest con el código de entrega
            val codigoEntregaRequest = CodigoEntregaRequest(codigoEntrega)

            // Hacer la llamada a la API
            val call: Call<Void> = apiService.entregarPedido(nombreRepartidor, codigoEntregaRequest)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Éxito al entregar el pedido
                        callback(true)
                    } else {
                        // Manejar la respuesta de error
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Manejar el fallo en la comunicación
                    callback(false)
                }
            })
        }
        override fun onMapReady(gMap: GoogleMap) {
            gMap?.let {
                googleMap = it

                // Activar la capa de ubicación en el mapa
                if (ActivityCompat.checkSelfPermission(
                        itemView.context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    googleMap.isMyLocationEnabled = true

                    // Obtener la última ubicación conocida del usuario
                    val fusedLocationClient =
                        LocationServices.getFusedLocationProviderClient(itemView.context)
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            // Centrar el mapa en la ubicación actual del usuario
                            location?.let {
                                val latLng = LatLng(it.latitude, it.longitude)
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                                // Enviar datos al servidor Flask
                                enviarDatosAlServidor(username, it)
                            }
                        }
                } else {
                    // Manejar la situación si no se otorgan permisos de ubicación
                    // Puedes solicitar permisos aquí o mostrar un mensaje al usuario
                }
            }
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

            val url = AppConfig.buildApiUrl("obtenerUbicacionUsuario")

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
                        handler.post {
                            val latLngUsuario =
                                LatLng(ubicacionUsuario.latitude, ubicacionUsuario.longitude)
                            val latLngRepartidor = LatLng(latitud, longitud)

                            // Limpiar marcadores existentes
                            googleMap.clear()

                            // Agregar marcador para la ubicación del repartidor
                            googleMap.addMarker(
                                MarkerOptions().position(latLngRepartidor).title(nombre).icon(
                                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                                )
                            )

                            // Mover la cámara al punto medio entre las dos ubicaciones solo si no se ha movido antes
                            if (!cameraMoved) {
                                val boundsBuilder = LatLngBounds.builder().include(latLngUsuario)
                                    .include(latLngRepartidor)
                                val bounds = boundsBuilder.build()
                                val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50)
                                googleMap.moveCamera(cameraUpdate)

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val binding =
            ItemPedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PedidoViewHolder(binding, fragmentManager)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedidoAsignado = listaPedidoAsignado.getOrNull(position)
        pedidoAsignado?.let { holder.bind(it, holder.itemView.context) }
    }

    override fun getItemCount(): Int = listaPedidoAsignado.size

    fun actualizarPedidos(nuevaLista: List<PedidoAsignado>?) {
        nuevaLista?.let {
            listaPedidoAsignado.clear()
            listaPedidoAsignado.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun agregarPedido(pedidoAsignado: PedidoAsignado?) {
        pedidoAsignado?.let {
            listaPedidoAsignado.add(it)
            notifyItemInserted(listaPedidoAsignado.size - 1)
        }
    }

    fun borrarTodosPedidos() {
        listaPedidoAsignado.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): PedidoAsignado = listaPedidoAsignado[position]
}