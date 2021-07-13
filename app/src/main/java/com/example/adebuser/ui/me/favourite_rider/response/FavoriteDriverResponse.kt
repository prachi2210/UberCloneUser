package com.example.adebuser.ui.me.favourite_rider.response

 data class FavoriteDriverResponse(
    val favoriteDriverList: List<FavoriteDriver>,
    val msg: String,
    val status: String
)

data class FavoriteDriver(
    val created_at: String,
    val favoriteRef: String,
    val id: String,
    val name: String,
    val phoneNumber: String,
    val profilePic: Any,
    val updated_at: String,
    val userRef: String
)