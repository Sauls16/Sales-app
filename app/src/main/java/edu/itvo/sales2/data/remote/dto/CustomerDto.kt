package edu.itvo.sales2.data.remote.dto

import com.google.gson.annotations.SerializedName


data class CustomerDto (
    @SerializedName("code")
    val code: String,
    val name: String,
    val email: String,
)
