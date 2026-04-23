package edu.itvo.sales2.data.remote.api

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("success")
    val successval : Boolean,
    val data: T
)
