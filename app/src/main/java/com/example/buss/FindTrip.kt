package com.example.buss

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FindTrip(

    @SerializedName("succeeded" ) var succeeded : Boolean? = null,
    @SerializedName("message"   ) var message   : String?  = null,
    @SerializedName("errors"    ) var errors    : String?  = null,
    @SerializedName("data"      ) var data      : Data?    = Data()
)

data class Data (

    @SerializedName("departureCityName" ) var departureCityName : String?          = null,
    @SerializedName("arrivalCityName"   ) var arrivalCityName   : String?          = null,
    @SerializedName("dateDay"           ) var dateDay           : String?          = null,
    @SerializedName("trips"             ) var trips             : ArrayList<Trips> = arrayListOf()

)

data class Trips (

    @SerializedName("departureTime"        ) var departureTime        : String? = null,
    @SerializedName("arrivalTime"          ) var arrivalTime          : String? = null,
    @SerializedName("priceTotal"           ) var priceTotal           : Int?    = null,
    @SerializedName("pricePerPerson"       ) var pricePerPerson       : Int?    = null,
    @SerializedName("hours"                ) var hours                : Int?    = null,
    @SerializedName("minutes"              ) var minutes              : Int?    = null,
    @SerializedName("seatCount"            ) var seatCount            : Int?    = null,
    @SerializedName("departureStationName" ) var departureStationName : String? = null,
    @SerializedName("arrivalStationName"   ) var arrivalStationName   : String? = null,
    @SerializedName("busBrandName"         ) var busBrandName         : String? = null,
    @SerializedName("busBrandLogoUrl"      ) var busBrandLogoUrl      : String? = null,
    @SerializedName("purchaseLink"         ) var purchaseLink         : String? = null

)