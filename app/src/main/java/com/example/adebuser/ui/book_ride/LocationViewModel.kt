package com.example.adebuser.ui.book_ride

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

open class LocationViewModel : ViewModel() {

    val latLong = MutableLiveData<LatLng>()
    val setPickupLatLng = MutableLiveData<LatLng>()
    val setDropOffLatLng = MutableLiveData<LatLng>()

    fun setLatLong(item: LatLng) {
        latLong.value = item
    }


    fun setMapLatLng(pickUp: LatLng, dropOff: LatLng) {
        setPickupLatLng.value = pickUp
        setDropOffLatLng.value = dropOff

    }

}