package com.example.mymap.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.mymap.R
import com.example.mymap.databinding.MainFragmentBinding
import com.example.mymap.network.datamodel.Daily
import com.example.mymap.network.errorhandling.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
        const val REQUEST_LOCATION_PERMISSION = 1000
    }

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: MainFragmentBinding

    private lateinit var position: LatLng
    private val iranLatLng: LatLng = LatLng(32.19690, 54.37704)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            viewModel.map = map
            enableMyLocation()

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(iranLatLng, 4f))

            viewModel.map.setOnMarkerClickListener {
                it.remove()
                return@setOnMarkerClickListener true
            }
        }

        binding.location.setOnClickListener {
            binding.cardView.visibility = View.INVISIBLE
            position = addMarkerToCenterCamera()
            viewModel.getWeather(position.latitude, position.longitude)
        }

        viewModel.latLngResponse.observe(viewLifecycleOwner) { it ->
            if (it.status == Status.ERROR) {
                Timber.i(it.message.toString())
            } else {
                val getData = it.data
               viewModel.temp = getData?.current?.temp.toString().substring(0..1)

              /*  val data = getData?.current?.temp.toString()
                data.substring(0,data.indexOf(".")-1)
                viewModel.temp = data*/

                getData?.daily?.let {
                    createRecyclerView(it)

                }
            }
        }
    }

    private fun createRecyclerView(dailyList: List<Daily>) {
        val adapter = GroupieAdapter()

        val dailyListItem = dailyList.map(::DailyItem)
        adapter.addAll(dailyListItem)

        binding.rvDaily.adapter = adapter
    }

    private fun addMarkerToCenterCamera(): LatLng {
        viewModel.centerLocation = viewModel.map.cameraPosition.target
        addMarker(viewModel.centerLocation)
        return viewModel.centerLocation
    }

    private fun addMarker(latLng: LatLng) {
        viewModel.map.addMarker(
            MarkerOptions().position(latLng).title(
                viewModel.temp
            )
        )?.showInfoWindow()
    }

    private fun enableMyLocation(){
        if (isPermissionGranted()){
            viewModel.map.isMyLocationEnabled = true
        }else{
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode : Int,
        permissions : Array<out String>,
        grantResults : IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions,
            grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION){
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                enableMyLocation()
            }
        }
    }

    private fun isPermissionGranted():Boolean{
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}


