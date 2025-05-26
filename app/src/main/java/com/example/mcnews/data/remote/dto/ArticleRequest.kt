// app/src/main/java/com/example/mcnews/data/remote/dto/ArticleRequest.kt
package com.example.mcnews.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArticleRequest(
    @SerializedName("AuthorId") val authorId: Int,
    @SerializedName("Title") val title: String,
    @SerializedName("Body") val body: String,
    @SerializedName("StatusId") val statusId: Int,
    @SerializedName("Image") val imageUrl: String? = null,
    @SerializedName("TagIds") val tagIds: List<Int> = emptyList()
)