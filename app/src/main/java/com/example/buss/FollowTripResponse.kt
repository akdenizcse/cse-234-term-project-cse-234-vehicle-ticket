package com.example.buss

import com.google.gson.annotations.SerializedName


data class FollowTripResponse (

    @SerializedName("succeeded" ) var succeeded : Boolean? = null,
    @SerializedName("message"   ) var message   : String?  = null,
    @SerializedName("errors"    ) var errors    : String?  = null,
    @SerializedName("data"      ) var data      : Int?     = null

)

data class FollowTripRequest(
    val city1CountryCode: String,
    val city1NameOrPlateCode: String,
    val city2CountryCode: String,
    val city2NameOrPlateCode: String,
    val date: String
)