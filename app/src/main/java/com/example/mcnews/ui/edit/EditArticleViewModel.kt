// app/src/main/java/com/example/mcnews/ui/edit/EditArticleViewModel.kt
package com.example.mcnews.ui.edit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcnews.data.remote.dto.ArticleRequest
import com.example.mcnews.domain.model.Article
import com.example.mcnews.domain.model.Status
import com.example.mcnews.domain.model.User
import com.example.mcnews.domain.repo.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditArticleViewModel @Inject constructor(
    private val repo: ArticleRepository
) : ViewModel() {

    val article = MutableLiveData<Article>()

    fun load(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedArticle = repo.getArticle(id)
                Log.d("EditArticleViewModel", "Loaded article: id=$id, title=${fetchedArticle?.title}")
                article.postValue(fetchedArticle)
            } catch (e: Exception) {
                Log.e("EditArticleViewModel", "Load error: ${e.message}", e)
            }
        }
    }

    suspend fun save(
        id: Int,
        title: String,
        body: String,
        statusId: Int,
        authorId: Int
    ): Boolean {
        val art = Article(
            articleId = id,
            authorId = authorId,
            title = title,
            body = body,
            statusId = statusId,
            imageUrl = null,
            createdAt = if (id == -1) java.time.Instant.now().toString() else "",
            tags = emptyList(),
            author = User(
                userId = authorId,
                firstName = "",
                lastName = "",
                middleName = null,
                birthDate = "1970-01-01",
                genderId = 1,
                email = "",
                login = "",
                passwordHash = "",
                photo = null,
                createdAt = "1970-01-01T00:00:00"
            ),
            status = Status(
                statusId = statusId,
                name = when (statusId) {
                    1 -> "Draft"
                    2 -> "Moderation"
                    3 -> "Rejected"
                    4 -> "Published"
                    else -> "Unknown"
                }
            )
        )
        return try {
            Log.d("EditArticleViewModel", "Saving article: id=$id, title=$title, authorId=$authorId, statusId=$statusId")
            if (id == -1) {
                repo.createArticle(art)
            } else {
                repo.updateArticle(art)
            }
            true
        } catch (e: Exception) {
            Log.e("EditArticleViewModel", "Save error: ${e.message}", e)
            false
        }
    }

    suspend fun delete(id: Int) {
        try {
            Log.d("EditArticleViewModel", "Deleting article: id=$id")
            repo.deleteArticle(id)
        } catch (e: Exception) {
            Log.e("EditArticleViewModel", "Delete error: ${e.message}", e)
        }
    }
}