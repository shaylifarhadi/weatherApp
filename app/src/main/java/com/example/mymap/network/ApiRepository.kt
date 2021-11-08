package com.example.mymap.network

import com.example.mymap.network.datamodel.ResponseWeather
import com.example.mymap.network.errorhandling.Resource

interface ApiRepository {

    suspend fun getWeather(lat:Double,lon:Double,app_id:String ): Resource<ResponseWeather>

}