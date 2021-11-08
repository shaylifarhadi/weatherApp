package com.example.mymap.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mymap.R
import com.example.mymap.databinding.MainFragmentBinding
import com.example.mymap.network.datamodel.Daily
import com.example.mymap.network.errorhandling.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
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
                viewModel.temp = getData?.current?.temp.toString()

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
}


