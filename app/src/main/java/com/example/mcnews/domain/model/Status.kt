package com.example.mcnews.domain.model

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("StatusId") val statusId: Int,
    @SerializedName("Name") val name: String
)