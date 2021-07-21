package com.example.adebuser.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.repository.AppRepository
import com.example.adebuser.ui.auth.AuthViewModel
import com.example.adebuser.ui.book_ride.BookRideViewModel
import com.example.adebuser.ui.book_ride.GetUserBookingStatusViewModel
import com.example.adebuser.ui.book_ride.ride_details.GetRideDetailsViewModel
import com.example.adebuser.ui.me.favourite_rider.FavoriteDriverViewModel

class ViewModelProviderFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(AppRepository(apiHelper)) as T
        }

        if (modelClass.isAssignableFrom(FavoriteDriverViewModel::class.java)) {
            return FavoriteDriverViewModel(AppRepository(apiHelper)) as T
        }

        if (modelClass.isAssignableFrom(BookRideViewModel::class.java)) {
            return BookRideViewModel(AppRepository(apiHelper)) as T
        }


        if (modelClass.isAssignableFrom(GetRideDetailsViewModel::class.java)) {
            return GetRideDetailsViewModel(AppRepository(apiHelper)) as T
        }

        if (modelClass.isAssignableFrom(GetUserBookingStatusViewModel::class.java)) {
            return GetUserBookingStatusViewModel(AppRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}






