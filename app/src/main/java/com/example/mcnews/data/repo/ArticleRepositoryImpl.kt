package com.example.mcnews.data.repo

import android.content.Context
import android.util.Log
import com.example.mcnews.data.remote.dto.ArticleRequest
import com.example.mcnews.domain.model.Article
import com.example.mcnews.domain.model.Tag
import com.example.mcnews.domain.model.User
import com.example.mcnews.domain.repo.ArticleRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

/**
 * Реализация репозитория для работы со статьями, взаимодействующая с API.
 * Поддерживает получение, создание, обновление, удаление статей, а также получение тегов и авторов.
 */
class ArticleRepositoryImpl @Inject constructor(
    private val context: Context,
    private val client: OkHttpClient
) : ArticleRepository {

    /**
     * Получает список статей с пагинацией и фильтрацией по статусу.
     */
    override suspend fun getArticles(skip: Int, limit: Int, status: Int?): List<Article> {
        val url = "http://10.0.2.2:8000/articles/?skip=$skip&limit=$limit${status?.let { "&status=$it" } ?: ""}"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                Log.d("ArticleRepository", "Get articles response: ${response.code}")
                if (response.isSuccessful) {
                    val body = response.body?.string() ?: return emptyList()
                    Log.d("ArticleRepository", "Raw response: $body")
                    Gson().fromJson(body, object : TypeToken<List<Article>>() {}.type)
                } else {
                    Log.e("ArticleRepository", "Get articles failed: ${response.body?.string()}")
                    emptyList()
                }
            }
        } catch (e: Exception) {
            Log.e("ArticleRepository", "Get articles error: ${e.message}", e)
            emptyList()
        }
    }

    /**
     * Получает статью по её ID.
     */
    override suspend fun getArticle(id: Int): Article? {
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/articles/$id")
            .get()
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                Log.d("ArticleRepository", "Get article $id response: ${response.code}")
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    Log.d("ArticleRepository", "Raw response for article $id: $body")
                    body?.let { Gson().fromJson(it, Article::class.java) }
                } else {
                    Log.e("ArticleRepository", "Get article failed: ${response.body?.string()}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("ArticleRepository", "Get article error: ${e.message}", e)
            null
        }
    }

    /**
     * Создаёт новую статью, отправляя POST-запрос к API.
     */
    override suspend fun createArticle(article: Article): Boolean {
        val requestBody = Gson().toJson(
            ArticleRequest(
                authorId = article.authorId,
                title = article.title,
                body = article.body,
                statusId = article.statusId,
                imageUrl = article.imageUrl,
                tagIds = article.tags.map { it.tagId }
            )
        )
        Log.d("ArticleRepository", "Create article request body: $requestBody")
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/articles/")
            .post(requestBody.toRequestBody("application/json".toMediaType()))
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                Log.d("ArticleRepository", "Create article response: ${response.code}")
                if (response.isSuccessful) {
                    Log.d("ArticleRepository", "Create article response body: ${response.body?.string()}")
                    true
                } else {
                    Log.e("ArticleRepository", "Create article failed: code=${response.code}, error=${response.body?.string()}")
                    false
                }
            }
        } catch (e: Exception) {
            Log.e("ArticleRepository", "Create article error: ${e.message}", e)
            false
        }
    }

    /**
     * Обновляет существующую статью, отправляя PUT-запрос к API.
     */
    override suspend fun updateArticle(article: Article): Boolean {
        val requestBody = Gson().toJson(
            ArticleRequest(
                authorId = article.authorId,
                title = article.title,
                body = article.body,
                statusId = article.statusId,
                imageUrl = article.imageUrl,
                tagIds = article.tags.map { it.tagId }
            )
        )
        Log.d("ArticleRepository", "Update article request body: $requestBody")
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/articles/${article.articleId}")
            .put(requestBody.toRequestBody("application/json".toMediaType()))
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                Log.d("ArticleRepository", "Update article response: ${response.code}")
                if (response.isSuccessful) {
                    Log.d("ArticleRepository", "Update article response body: ${response.body?.string()}")
                    true
                } else {
                    Log.e("ArticleRepository", "Update article failed: code=${response.code}, error=${response.body?.string()}")
                    false
                }
            }
        } catch (e: Exception) {
            Log.e("ArticleRepository", "Update article error: ${e.message}", e)
            false
        }
    }

    /**
     * Удаляет статью по её ID, отправляя DELETE-запрос к API.
     */
    override suspend fun deleteArticle(id: Int): Boolean {
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/articles/$id")
            .delete()
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                Log.d("ArticleRepository", "Delete article response: ${response.code}")
                response.isSuccessful
            }
        } catch (e: Exception) {
            Log.e("ArticleRepository", "Delete article error: ${e.message}", e)
            false
        }
    }

    /**
     * Получает список тегов.
     */
    override suspend fun getTags(): List<Tag> {
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/tags/")
            .get()
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                Log.d("ArticleRepository", "Get tags response: ${response.code}")
                if (response.isSuccessful) {
                    val body = response.body?.string() ?: return emptyList()
                    Log.d("ArticleRepository", "Raw tags response: $body")
                    Gson().fromJson(body, object : TypeToken<List<Tag>>() {}.type)
                } else {
                    Log.e("ArticleRepository", "Get tags failed: ${response.body?.string()}")
                    emptyList()
                }
            }
        } catch (e: Exception) {
            Log.e("ArticleRepository", "Get tags error: ${e.message}", e)
            emptyList()
        }
    }

    /**
     * Получает список авторов (пользователей).
     */
    override suspend fun getAuthors(): List<User> {
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/users/")
            .get()
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                Log.d("ArticleRepository", "Get authors response: ${response.code}")
                if (response.isSuccessful) {
                    val body = response.body?.string() ?: return emptyList()
                    Log.d("ArticleRepository", "Raw authors response: $body")
                    Gson().fromJson(body, object : TypeToken<List<User>>() {}.type)
                } else {
                    Log.e("ArticleRepository", "Get authors failed: ${response.body?.string()}")
                    emptyList()
                }
            }
        } catch (e: Exception) {
            Log.e("ArticleRepository", "Get authors error: ${e.message}", e)
            emptyList()
        }
    }
}