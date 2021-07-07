package com.example.adebuser.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.repository.AppRepository
import com.example.adebuser.ui.auth.AuthViewModel

class ViewModelProviderFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(AppRepository(apiHelper)) as T
        }
/*
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(AppRepository(apiHelper)) as T
        }

        if (modelClass.isAssignableFrom(NavigationViewModel::class.java)) {
            return NavigationViewModel(AppRepository(apiHelper)) as T
        }*/
        throw IllegalArgumentException("Unknown class name")
    }
}






