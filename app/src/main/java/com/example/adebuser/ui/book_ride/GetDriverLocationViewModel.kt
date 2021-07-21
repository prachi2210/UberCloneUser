package com.example.adebuser.ui.book_ride

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.adebuser.data.api.repository.AppRepository
import com.example.adebuser.ui.book_ride.booking_request.BookRideRequest
import com.example.adebuser.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class GetDriverLocationViewModel(private val appRepository: AppRepository) : ViewModel() {


    fun getDriverLocation(
        driverRef: String
    ) = liveData(Dispatchers.IO) {

        val userReference =
            driverRef.toRequestBody("multipart/form-data".toMediaType())


        emit(Resource.loading(data = null))
        try {

            emit(
                Resource.success(
                    data = appRepository.getDriverLocation(
                        userReference
                    )
                )
            )

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}