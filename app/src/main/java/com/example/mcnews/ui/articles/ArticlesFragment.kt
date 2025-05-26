// app/src/main/java/com/example/mcnews/ui/articles/ArticlesFragment.kt
package com.example.mcnews.ui.articles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mcnews.R
import com.example.mcnews.data.remote.ApiService
import com.example.mcnews.databinding.FragmentArticlesBinding
import com.example.mcnews.ui.edit.EditArticleActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesFragment : Fragment() {

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArticlesViewModel by viewModels()

    @Inject lateinit var api: ApiService

    private val tagNames = mutableListOf<String>()
    private val tagIdMap = mutableMapOf<Int, Int?>()

    private val editArticleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            Log.d("ArticlesFragment", "Received result from EditArticleActivity, refreshing articles")
            viewModel.load() // Обновить ленту
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArticlesAdapter(
            onClick = { article ->
                startActivity(Intent(requireContext(), ArticleDetailActivity::class.java).apply {
                    putExtra("title", article.title)
                    putExtra("body", article.body)
                    putExtra("imageUrl", article.imageUrl)
                })
            },
            onLongClick = { article ->
                val intent = Intent(requireContext(), EditArticleActivity::class.java)
                intent.putExtra("articleId", article.articleId)
                editArticleLauncher.launch(intent)
            }
        )
        binding.rvArticles.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvArticles.visibility = View.GONE
                }
                is State.Data -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvArticles.visibility = View.VISIBLE
                    adapter.submitList(state.articles)
                    Log.d("ArticlesFragment", "Submitted ${state.articles.size} articles to adapter")
                }
                is State.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvArticles.visibility = View.GONE
                    Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                    Log.e("ArticlesFragment", "Error: ${state.message}")
                }
            }
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_articles, menu)

                val spinner = menu.findItem(R.id.action_filter).actionView as AppCompatSpinner
                val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tagNames).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
                spinner.adapter = spinnerAdapter

                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val tags = api.getTags()
                        Log.d("ArticlesFragment", "Loaded ${tags.size} tags")
                        tagNames.clear()
                        tagNames.add("Все теги")
                        tagIdMap.clear()
                        tagIdMap[0] = null
                        tags.forEachIndexed { index, tag ->
                            tagNames.add(tag.name)
                            tagIdMap[index + 1] = tag.tagId
                        }
                        requireActivity().runOnUiThread {
                            spinnerAdapter.notifyDataSetChanged()
                        }
                    } catch (e: Exception) {
                        Log.e("ArticlesFragment", "Failed to load tags: ${e.message}", e)
                        requireActivity().runOnUiThread {
                            Snackbar.make(binding.root, "Не удалось загрузить теги: ${e.message}", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            Log.d("ArticlesFragment", "Selected tag position: $position, tagId: ${tagIdMap[position]}")
                            viewModel.load(tagId = tagIdMap[position])
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) = Unit
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_add_article -> {
                        val intent = Intent(requireContext(), EditArticleActivity::class.java)
                        editArticleLauncher.launch(intent)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}