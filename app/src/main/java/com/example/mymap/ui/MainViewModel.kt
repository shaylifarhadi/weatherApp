package com.example.mymap.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymap.network.ApiRepository
import com.example.mymap.network.datamodel.ResponseWeather
import com.example.mymap.network.errorhandling.Resource
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    lateinit var map: GoogleMap

    var temp:String = ""

    lateinit var centerLocation: LatLng

    private val _latLngResponse = MutableLiveData<Resource<ResponseWeather>>()
    val latLngResponse: LiveData<Resource<ResponseWeather>>
        get() = _latLngResponse


    fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _latLngResponse.value =
                apiRepository.getWeather(latitude,longitude, "0ed3182b5f85be6973f5ac0d3b81167e")
        }
    }
}