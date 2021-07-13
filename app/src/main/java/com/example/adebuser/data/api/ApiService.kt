package com.example.adebuser.data.api


import com.example.adebuser.ui.auth.response.LoginResponse
import com.example.adebuser.ui.auth.response.MessageResponse
import com.example.adebuser.ui.auth.response.SignUpResponse
import com.example.adebuser.ui.me.favourite_rider.response.FavoriteDriverResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @Multipart
    @POST("api/v1/userRegister")
    suspend fun signUp(
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("deviceType") deviceType: RequestBody?,
        @Part("deviceToken") deviceToken: RequestBody?,
    ): Response<SignUpResponse>


    @Multipart
    @POST("api/v1/userLogin")
    suspend fun login(
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("FieldType") fieldType: RequestBody?,
        @Part("deviceType") deviceType: RequestBody?,
        @Part("deviceToken") deviceToken: RequestBody?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?
    ): Response<LoginResponse>


    @Multipart
    @POST("api/v1/userOut")
    suspend fun logout(
        @Part("userRef") userRef: RequestBody?
    ): Response<MessageResponse>

    @Multipart
    @POST("api/v1/otpVerify")
    suspend fun otpVerify(
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("otp") otp: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/ForgetPassword")
    suspend fun forgotPassword(
        @Part("FieldType") fieldType: RequestBody?,
        @Part("phoneNumber") phoneNumber: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/resendOtp")
    suspend fun resendOtp(
        @Part("phoneNumber") phoneNumber: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/CreateNewPassword")
    suspend fun createNewPassword(
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("password") password: RequestBody?
    ): Response<MessageResponse>


 @Multipart
    @POST("api/v1/getFavoriteDriver")
    suspend fun getFavoriteDrivers(
        @Part("userRef") userRef: RequestBody?
    ): Response<FavoriteDriverResponse>


    @Multipart
    @POST("bookingConfirmation")
    suspend fun confirmBooking(
        @Part("userRef") userRef:RequestBody,
        @Part("chooseType") chooseType:RequestBody,
        @Part("pickupLat") pickupLat:RequestBody,
        @Part("pickupLog") pickupLong:RequestBody,
        @Part("dropOffLat") dropOffLat:RequestBody,
        @Part("dropOffLog") dropOffLong:RequestBody,
        @Part("gearType") gearType:RequestBody,
        @Part("carType") carType:RequestBody,
        @Part("scheduleTime") scheduleTime:RequestBody,
        @Part("paymentMode") paymentMode:RequestBody,
        @Part("fareAmount") fareAmount:RequestBody
    )

}






