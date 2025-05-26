package com.example.mcnews.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorDto(
    @Json(name = "UserId") val userId: Int,
    @Json(name = "FirstName") val firstName: String,
    @Json(name = "LastName")  val lastName:  String
    /* …что хотите отдать… */
)