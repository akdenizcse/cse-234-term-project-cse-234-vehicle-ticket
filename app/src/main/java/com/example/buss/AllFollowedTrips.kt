package com.example.buss

import com.google.gson.annotations.SerializedName

data class AllFollowedTrips (

    @SerializedName("succeeded" ) var succeeded : Boolean?        = null,
    @SerializedName("message"   ) var message   : String?         = null,
    @SerializedName("errors"    ) var errors    : String?         = null,
    @SerializedName("data"      ) var data      : List<DataFollowTrip> = listOf()

)
data class DataFollowTrip (

    @SerializedName("deleteId"         ) var deleteId         : String? = null,
    @SerializedName("city1CountryCode" ) var city1CountryCode : String? = null,
    @SerializedName("city1Name"        ) var city1Name        : String? = null,
    @SerializedName("city1Id"          ) var city1Id          : Int?    = null,
    @SerializedName("city2CountryCode" ) var city2CountryCode : String? = null,
    @SerializedName("city2Name"        ) var city2Name        : String? = null,
    @SerializedName("city2Id"          ) var city2Id          : Int?    = null,
    @SerializedName("date"             ) var date             : String? = null,
    @SerializedName("lastTimeMailSent" ) var lastTimeMailSent : String? = null

)