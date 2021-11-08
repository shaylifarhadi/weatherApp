package com.example.mymap.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mymap.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

/*const val REQUEST_LOCATION_PERMISSION = 1000*/

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /* companion object{
         const val TAG = "ERROR"
     }*/
    private lateinit var binding: ActivityMainBinding


    //  var startLatLng: LatLng? = null
    // var endLatLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*   val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync { map ->
            this.map = map
            enableMyLocation()
           map.moveCamera(CameraUpdateFactory.newLatLngZoom(tehranLatLng, 10f))*/

        //        map.addMarker(MarkerOptions().position(tehranLatLng).title("tehran").draggable(true))

        //*    map.uiSettings.setAllGesturesEnabled(true)

        /*     map.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                    override fun onMarkerDragStart(p0: Marker?) {

                    }

                    override fun onMarkerDrag(marker: Marker?) {

                    }

                    override fun onMarkerDragEnd(marker: Marker?) {
                        val lat = marker?.position?.latitude
                        val lng = marker?.position?.longitude
                        Toast.makeText(this@MainActivity, "$lat $lng", Toast.LENGTH_SHORT).show()
                    }

                })

                map.setOnMapLongClickListener(::addMarker)

                map.setOnMarkerClickListener {
                    it.remove()
                    return@setOnMarkerClickListener true
                }



            binding.confirmLocation.setOnClickListener {
                if (startLatLng == null) {
                    startLatLng = addMarkerToCenterCamera("start")
                    binding.confirmLocation.text = "تایید مقصد"
                } else {
                    endLatLng = addMarkerToCenterCamera("end")
                }

            }

        }
    }

    private fun addMarkerToCenterCamera(title: String): LatLng {
        val centerLocation = map.cameraPosition.target
        addMarker(centerLocation, title)
        return centerLocation
    }

    private fun addMarker(latLng: LatLng, title: String? = "tehran") {
        map.addMarker(MarkerOptions().position(latLng).title(title))
    }

    private fun enableMyLocation() {
        try {
            if (isPermissionGranted()) {
                map.isMyLocationEnabled = true
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            }
        }catch (e:SecurityException){
            Log.i(TAG, "error")
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.)
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }


    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    }
}
*/

    }
}