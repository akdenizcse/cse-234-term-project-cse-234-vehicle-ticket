package com.example.buss

data class AllCitiesNames(

    val succeeded: Boolean,
    val message: String?,
    val errors: List<String>?,
    val data: List<String>

)
