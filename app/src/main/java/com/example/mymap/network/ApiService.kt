package com.example.mymap.network

import com.example.mymap.network.datamodel.ResponseWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("2.5/onecall?units=metric")
   suspend fun getWeather(@Query("lat") lat:Double, @Query("lon") lon:Double,
                          @Query("appid") apikey:String ): ResponseWeather
}