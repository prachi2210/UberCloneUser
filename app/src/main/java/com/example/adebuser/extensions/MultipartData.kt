package com.example.adebuser.extensions

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


fun File.getMultipart(key: String, type: String): MultipartBody.Part {
    val requestFile = RequestBody.create(type.toMediaTypeOrNull(), this)
    return MultipartBody.Part.createFormData(key, this.name, requestFile)
}


fun String.getMultipart(key: String, type: String): MultipartBody.Part {
    val requestFile = RequestBody.create(type.toMediaTypeOrNull(), "")
    return MultipartBody.Part.createFormData(key, this, requestFile)
}

fun String.getRequestBody(): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), this)
}

fun String.getVideoMultipart(key: String): MultipartBody.Part {
    val videoFile = File(this)
    val videoBody = RequestBody.create("video/*".toMediaTypeOrNull(), videoFile)
    return MultipartBody.Part.createFormData(key, videoFile.name, videoBody)
}

fun String.getMultipartFromPath(key: String, type: String): MultipartBody.Part? {
    return if (this != null && this != "null" && this.isNotEmpty()) {
        val file = File(this)
        file.getMultipart(key, type)
    } else {
        "".getMultipart(key, type)
    }
}