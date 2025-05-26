package com.example.mcnews.domain.model

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("TagId") val tagId: Int,
    @SerializedName("Name") val name: String
)