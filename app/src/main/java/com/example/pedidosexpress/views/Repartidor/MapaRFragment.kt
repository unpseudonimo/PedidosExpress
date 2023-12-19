package com.example.pedidosexpress.views.Repartidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.pedidosexpress.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import java.util.Timer

class MapaRFragment : Fragment() {

    private val mMap: GoogleMap? = null
    private val productList: ListView? = null
    private val products: List<Map<String, String>>? = null
    private val fusedLocationClient: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest? = null
    private val locationCallback: LocationCallback? = null
    private val previousLocation: LatLng? = null
    private val myLocationMarker: Marker? = null
    private val proximityCircle: CircleOptions? = null
    private val otherUserLocations = ArrayList<LatLng>()
    private val timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapa, container, false)
    }

    companion object {

    }
}