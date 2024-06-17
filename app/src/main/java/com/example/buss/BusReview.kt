package com.example.buss

import com.google.gson.annotations.SerializedName

data class BusReview (

    @SerializedName("succeeded" ) var succeeded : Boolean?        = null,
    @SerializedName("message"   ) var message   : String?         = null,
    @SerializedName("errors"    ) var errors    : String?         = null,
    @SerializedName("data"      ) var data      : List<ReviewData> = listOf()

)

data class ReviewData (

    @SerializedName("busId"       ) var busId       : Int?    = null,
    @SerializedName("userName"    ) var userName    : String? = null,
    @SerializedName("commentText" ) var commentText : String? = null,
    @SerializedName("rate"        ) var rate        : Int?    = null,
    @SerializedName("time"        ) var time        : String? = null

)