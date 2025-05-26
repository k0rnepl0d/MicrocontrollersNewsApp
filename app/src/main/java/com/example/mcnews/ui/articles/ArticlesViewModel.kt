// app/src/main/java/com/example/mcnews/ui/articles/ArticlesViewModel.kt
package com.example.mcnews.ui.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcnews.domain.model.Article
import com.example.mcnews.domain.repo.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface State {
    object Loading : State
    data class Data(val articles: List<Article>) : State
    data class Error(val message: String) : State
}

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val repo: ArticleRepository
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun load(status: Int? = null, tagId: Int? = null, skip: Int = 0, limit: Int = 100) = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(State.Loading)
        try {
            val articles = repo.getArticles(skip = skip, limit = limit, status = status)
            Log.d("ArticlesViewModel", "Loaded ${articles.size} articles")
            val filtered = tagId?.let { id -> articles.filter { art -> art.tags.any { it.tagId == id } } } ?: articles
            _state.postValue(State.Data(filtered))
        } catch (e: Exception) {
            Log.e("ArticlesViewModel", "Error loading articles: ${e.message}", e)
            _state.postValue(State.Error(e.message ?: "Неизвестная ошибка: ${e.javaClass.simpleName}"))
        }
    }
}