package com.example.buss

import com.google.gson.annotations.SerializedName

data class AllCities (

    @SerializedName("succeeded" ) var succeeded : Boolean?        = null,
    @SerializedName("message"   ) var message   : String?         = null,
    @SerializedName("errors"    ) var errors    : String?         = null,
    @SerializedName("data") var data: ArrayList<DataCities> = arrayListOf()

)
data class DataCities (

    @SerializedName("id"              ) var id              : Int?    = null,
    @SerializedName("countryCode"     ) var countryCode     : String? = null,
    @SerializedName("numberPlateCode" ) var numberPlateCode : String? = null,
    @SerializedName("name"            ) var name            : String? = null,
    @SerializedName("normalizedName"  ) var normalizedName  : String? = null,
    @SerializedName("description"     ) var description     : String? = null,
    @SerializedName("imageLink"       ) var imageLink       : String? = null

)