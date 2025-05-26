// app/src/main/java/com/example/mcnews/ui/edit/EditArticleActivity.kt
package com.example.mcnews.ui.edit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mcnews.R
import com.example.mcnews.data.remote.ApiService
import com.example.mcnews.databinding.ActivityArticleEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EditArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleEditBinding
    private val viewModel: EditArticleViewModel by viewModels()

    @Inject lateinit var api: ApiService

    private val statusPairs = listOf(
        "Черновик" to 1,
        "Модерация" to 2,
        "Отклонено" to 3,
        "Опубликовано" to 4
    )

    private val authorNames = mutableListOf<String>()
    private val authorIds = mutableListOf<Int>()
    private var authorsLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val articleId = intent.getIntExtra("articleId", -1)
        Log.d("EditArticleActivity", "Started with articleId: $articleId")
        binding.btnSave.isEnabled = false

        binding.spStatus.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusPairs.map { it.first }).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        val authorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, authorNames).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spAuthor.adapter = authorAdapter

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val authors = api.getAuthors()
                Log.d("EditArticleActivity", "Loaded ${authors.size} authors")
                authorNames.clear()
                authorIds.clear()
                authors.forEach {
                    authorNames.add("${it.firstName} ${it.lastName}")
                    authorIds.add(it.userId)
                    Log.d("EditArticleActivity", "Author: ${it.firstName} ${it.lastName}, ID: ${it.userId}")
                }
                runOnUiThread {
                    authorAdapter.notifyDataSetChanged()
                    authorsLoaded = true
                    binding.btnSave.isEnabled = true
                    Log.d("EditArticleActivity", "Author spinner updated, authorsLoaded: $authorsLoaded")
                    if (authorNames.isEmpty()) {
                        Log.w("EditArticleActivity", "No authors loaded")
                        Toast.makeText(this@EditArticleActivity, "Список авторов пуст", Toast.LENGTH_LONG).show()
                    }
                }
                if (articleId != -1) {
                    viewModel.load(articleId)
                }
            } catch (e: Exception) {
                Log.e("EditArticleActivity", "Failed to load authors: ${e.message}", e)
                runOnUiThread {
                    Toast.makeText(this@EditArticleActivity, "Не удалось загрузить авторов: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        if (articleId != -1) {
            binding.btnDelete.visibility = View.VISIBLE
            viewModel.article.observe(this@EditArticleActivity) { art ->
                if (art != null) {
                    binding.etTitle.setText(art.title)
                    binding.etBody.setText(art.body)
                    binding.spStatus.setSelection(statusPairs.indexOfFirst { it.second == art.statusId })
                    val idx = authorIds.indexOf(art.authorId)
                    if (idx >= 0) {
                        binding.spAuthor.setSelection(idx)
                        Log.d("EditArticleActivity", "Set author spinner to index: $idx, authorId: ${art.authorId}")
                    } else {
                        Log.w("EditArticleActivity", "Author ID ${art.authorId} not found in authorIds")
                        Toast.makeText(this@EditArticleActivity, "Автор статьи не найден в списке", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            binding.btnDelete.visibility = View.GONE
        }

        binding.btnSave.setOnClickListener {
            if (!authorsLoaded) {
                Log.w("EditArticleActivity", "Save attempted but authors not loaded")
                Toast.makeText(this@EditArticleActivity, "Список авторов ещё загружается", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val selectedAuthorId = authorIds.getOrNull(binding.spAuthor.selectedItemPosition)
                if (selectedAuthorId == null) {
                    Log.e("EditArticleActivity", "No author selected")
                    runOnUiThread {
                        Toast.makeText(this@EditArticleActivity, "Выберите автора", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }
                val title = binding.etTitle.text.toString().trim()
                val body = binding.etBody.text.toString().trim()
                val statusId = statusPairs[binding.spStatus.selectedItemPosition].second
                Log.d("EditArticleActivity", "Saving article: id=$articleId, title=$title, authorId=$selectedAuthorId, statusId=$statusId")
                if (title.isEmpty() || body.isEmpty()) {
                    runOnUiThread {
                        Toast.makeText(this@EditArticleActivity, "Заполните заголовок и текст", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }
                try {
                    val ok = viewModel.save(articleId, title, body, statusId, selectedAuthorId)
                    runOnUiThread {
                        if (ok) {
                            Toast.makeText(this@EditArticleActivity, "Статья сохранена", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK)
                            finish()
                        } else {
                            Toast.makeText(this@EditArticleActivity, "Ошибка сохранения статьи", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("EditArticleActivity", "Save error: ${e.message}", e)
                    runOnUiThread {
                        Toast.makeText(this@EditArticleActivity, "Ошибка сохранения: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            if (articleId != -1) {
                lifecycleScope.launch(Dispatchers.IO) {
                    Log.d("EditArticleActivity", "Deleting article with ID: $articleId")
                    try {
                        viewModel.delete(articleId)
                        runOnUiThread {
                            Toast.makeText(this@EditArticleActivity, "Статья удалена", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK)
                            finish()
                        }
                    } catch (e: Exception) {
                        Log.e("EditArticleActivity", "Delete error: ${e.message}", e)
                        runOnUiThread {
                            Toast.makeText(this@EditArticleActivity, "Ошибка удаления: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}