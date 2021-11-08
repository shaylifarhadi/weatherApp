
package com.example.mymap.network

import com.example.mymap.network.datamodel.ResponseWeather
import com.example.mymap.network.errorhandling.ResponseHandler
import com.example.mymap.network.errorhandling.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import java.lang.Exception

class ApiRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler
) : ApiRepository {

    override suspend fun getWeather(lat: Double, lon: Double, app_id: String
    ): Resource<ResponseWeather> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = apiService.getWeather(lat,lon,app_id)
                responseHandler.handleSuccess(result)
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }
}

