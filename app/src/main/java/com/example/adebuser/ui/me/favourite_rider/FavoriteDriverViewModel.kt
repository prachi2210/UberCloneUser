package com.example.adebuser.ui.me.favourite_rider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.adebuser.data.api.repository.AppRepository
import com.example.adebuser.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class FavoriteDriverViewModel(private val appRepository: AppRepository) : ViewModel() {


    fun getFavoriteDrivers(
        userRef: String,
        latitude: String,
        longitude: String,
        gearType: String,
        carType: String
    ) = liveData(Dispatchers.IO) {
        val userReference = userRef.toRequestBody("multipart/form-data".toMediaType())
        val lat = latitude.toRequestBody("multipart/form-data".toMediaType())
        val long = longitude.toRequestBody("multipart/form-data".toMediaType())
        val gearDetails = gearType.toRequestBody("multipart/form-data".toMediaType())
        val carDetails = carType.toRequestBody("multipart/form-data".toMediaType())


        emit(Resource.loading(data = null))
        try {

            emit(
                Resource.success(
                    data = appRepository.getFavoriteDriver(
                        userReference, lat, long, gearDetails, carDetails
                    )
                )
            )

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}