package com.example.example

import com.google.gson.annotations.SerializedName


data class RegisterResponse(
    @SerializedName("succeeded") val succeeded: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("errors") val errors: List<String>?,
    @SerializedName("data") val data: String?
)

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val userName: String,
    val password: String,
    val confirmPassword: String
)