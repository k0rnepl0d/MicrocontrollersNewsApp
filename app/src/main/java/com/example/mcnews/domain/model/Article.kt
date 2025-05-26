package com.example.mcnews.domain.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("ArticleId") val articleId: Int,
    @SerializedName("AuthorId") val authorId: Int,
    @SerializedName("Title") val title: String,
    @SerializedName("Body") val body: String,
    @SerializedName("StatusId") val statusId: Int,
    @SerializedName("Image") val imageUrl: String?,
    @SerializedName("CreatedAt") val createdAt: String,
    @SerializedName("Tags") val tags: List<Tag> = emptyList(),
    @SerializedName("Author") val author: User,
    @SerializedName("Status") val status: Status
)