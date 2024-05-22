package com.example.example

import com.google.gson.annotations.SerializedName


data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    @SerializedName("succeeded") val succeeded: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("errors") val errors: List<String>?,
    @SerializedName("data") val data: UserData?
)

data class UserData(
    @SerializedName("id") val id: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("email") val email: String,
    @SerializedName("roles") val roles: List<String>,
    @SerializedName("isVerified") val isVerified: Boolean,
    @SerializedName("jwToken") val jwToken: String
)