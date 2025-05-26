package com.example.mcnews.domain.repo

import com.example.mcnews.domain.model.Article
import com.example.mcnews.domain.model.Tag
import com.example.mcnews.domain.model.User

interface ArticleRepository {
    suspend fun getArticles(skip: Int, limit: Int, status: Int?): List<Article>
    suspend fun getArticle(id: Int): Article?
    suspend fun createArticle(article: Article): Boolean
    suspend fun updateArticle(article: Article): Boolean
    suspend fun deleteArticle(id: Int): Boolean
    suspend fun getTags(): List<Tag>
    suspend fun getAuthors(): List<User>
}