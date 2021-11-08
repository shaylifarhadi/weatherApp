package com.example.mymap.network.datamodel


import com.squareup.moshi.Json

data class WeatherX(
    @Json(name = "description")
    val description: String?,
    @Json(name = "icon")
    val icon: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "main")
    val main: String?
)