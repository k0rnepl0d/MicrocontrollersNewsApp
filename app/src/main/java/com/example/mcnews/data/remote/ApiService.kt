// app/src/main/java/com/example/mcnews/data/remote/ApiService.kt
package com.example.mcnews.data.remote

import com.example.mcnews.data.remote.dto.ArticleRequest
import com.example.mcnews.domain.model.Article
import com.example.mcnews.domain.model.Tag
import com.example.mcnews.domain.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("articles/")
    suspend fun getArticles(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100,
        @Query("status") status: Int? = null
    ): List<Article>

    @GET("articles/{id}")
    suspend fun getArticle(@Path("id") id: Int): Article

    @POST("articles/")
    suspend fun createArticle(@Body article: ArticleRequest): Article

    @PUT("articles/{id}")
    suspend fun updateArticle(@Path("id") id: Int, @Body article: ArticleRequest): Article

    @DELETE("articles/{id}")
    suspend fun deleteArticle(@Path("id") id: Int)

    @GET("tags/")
    suspend fun getTags(): List<Tag>

    @GET("users/")
    suspend fun getAuthors(): List<User>
}