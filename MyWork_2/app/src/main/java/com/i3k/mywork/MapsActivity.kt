package com.i3k.mywork

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var btnUsar: Button
    private lateinit var coordenadaSeleccionada: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        btnUsar = findViewById<Button>(R.id.btnUsar)
        btnUsar.setOnClickListener(){
            traducirCoord()

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
    }

    private fun traducirCoord(){
        val geocoder = Geocoder(this, Locale.getDefault())


        Thread {
            val direcciones = geocoder.getFromLocation(
                    coordenadaSeleccionada.latitude,
                    coordenadaSeleccionada.longitude,
                    1
            )

            if(direcciones.size > 0){
                val direccion = direcciones[0].getAddressLine(0)

                val intentdeRegreso = Intent()
                intentdeRegreso.putExtra("ubicacion", direccion)

                setResult(Activity.RESULT_OK, intentdeRegreso)
                finish()
            }
        }.start()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        activarMiLocacion()

        mMap.setOnMapClickListener {coordenada ->
            coordenadaSeleccionada = coordenada

            mMap.clear()
            mMap.addMarker(MarkerOptions().position(coordenada))
            btnUsar.isEnabled = true

        }

        // Add a marker in Sydney and move the camera
        val monterrey = LatLng(25.67, -100.30)
        mMap.addMarker(MarkerOptions().position(monterrey).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(monterrey, 12F))
    }

    private fun activarMiLocacion() {

        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true
    }
}