
package com.example.mcnews.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequestDto(
    @Json(name = "Login") val login: String,
    @Json(name = "PasswordHash") val password: String
)

@JsonClass(generateAdapter = true)
data class RegisterRequestDto(
    @Json(name = "Login") val login: String,
    @Json(name = "PasswordHash") val password: String,
    @Json(name = "FirstName") val firstName: String?,
    @Json(name = "LastName") val lastName: String?
)

@JsonClass(generateAdapter = true)
data class AuthResponseDto(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "token_type") val tokenType: String
)

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "UserId") val userId: Int,
    @Json(name = "Login") val login: String,
    @Json(name = "FirstName") val firstName: String?,
    @Json(name = "LastName") val lastName: String?
)
