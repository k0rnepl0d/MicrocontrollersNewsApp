
package com.example.mcnews.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mcnews.databinding.ItemArticleBinding
import com.example.mcnews.domain.model.Article

class ArticlesAdapter(
    private val onClick: (Article) -> Unit,
    private val onLongClick: (Article) -> Unit
) : ListAdapter<Article, ArticlesAdapter.ViewHolder>(ArticleDiffCallback()) {

    class ViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, onClick: (Article) -> Unit, onLongClick: (Article) -> Unit) {
            binding.tvTitle.text = article.title
            binding.tvBody.text = article.body
            binding.root.setOnClickListener { onClick(article) }
            binding.root.setOnLongClickListener { onLongClick(article); true }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick, onLongClick)
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem.articleId == newItem.articleId
    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem
}