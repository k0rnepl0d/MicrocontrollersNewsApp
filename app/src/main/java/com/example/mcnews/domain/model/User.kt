// app/src/main/java/com/example/mcnews/domain/model/User.kt
package com.example.mcnews.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("UserId") val userId: Int,
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("MiddleName") val middleName: String?,
    @SerializedName("BirthDate") val birthDate: String,
    @SerializedName("GenderId") val genderId: Int,
    @SerializedName("Email") val email: String,
    @SerializedName("Login") val login: String,
    @SerializedName("PasswordHash") val passwordHash: String,
    @SerializedName("Photo") val photo: String?,
    @SerializedName("CreatedAt") val createdAt: String
)