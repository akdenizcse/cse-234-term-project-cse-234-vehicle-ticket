package com.example.buss

import com.google.gson.annotations.SerializedName

data class AllBusBrands (

    @SerializedName("succeeded" ) var succeeded : Boolean?        = null,
    @SerializedName("message"   ) var message   : String?         = null,
    @SerializedName("errors"    ) var errors    : String?         = null,
    @SerializedName("data"      ) var data      : List<DataBus> = arrayListOf()

)

data class DataBus (

    @SerializedName("name"           ) var name           : String? = null,
    @SerializedName("normalizedName" ) var normalizedName : String? = null,
    @SerializedName("description"    ) var description    : String? = null,
    @SerializedName("imageLink"      ) var imageLink      : String? = null,
    @SerializedName("webSiteUrl"     ) var webSiteUrl     : String? = null,
    @SerializedName("userComments"   ) var userComments   : String? = null,
    @SerializedName("id"             ) var id             : Int?    = null

)