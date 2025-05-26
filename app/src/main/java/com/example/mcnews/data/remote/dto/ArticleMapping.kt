// app/src/main/java/com/example/mcnews/data/remote/dto/ArticleMapping.kt
package com.example.mcnews.data.remote.dto

import com.example.mcnews.domain.model.Article
import com.example.mcnews.domain.model.Status
import com.example.mcnews.domain.model.Tag
import com.example.mcnews.domain.model.User
import com.google.gson.annotations.SerializedName

data class ArticleMapping(
    @SerializedName("ArticleId") val articleId: Int,
    @SerializedName("AuthorId") val authorId: Int,
    @SerializedName("Title") val title: String,
    @SerializedName("Body") val body: String,
    @SerializedName("StatusId") val statusId: Int,
    @SerializedName("Image") val imageUrl: String?,
    @SerializedName("CreatedAt") val createdAt: String,
    @SerializedName("Tags") val tags: List<Tag> = emptyList(),
    @SerializedName("Author") val author: User, // Add if available in response
    @SerializedName("Status") val status: Status // Add if available in response
) {
    fun toArticle() = Article(
        articleId = articleId,
        authorId = authorId,
        title = title,
        body = body,
        statusId = statusId,
        imageUrl = imageUrl,
        createdAt = createdAt,
        tags = tags,
        author = author,
        status = status
    )
}