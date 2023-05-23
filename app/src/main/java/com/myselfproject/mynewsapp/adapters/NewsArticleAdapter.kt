package com.myselfproject.mynewsapp.adapters


import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myselfproject.mynewsapp.clicklistener.OnItemClickListener
import com.myselfproject.mynewsapp.databinding.NewsItemBinding
import com.myselfproject.mynewsapp.models.NewsArticle
import java.text.SimpleDateFormat
import java.util.*


class NewsArticleAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<NewsArticle, NewsArticleAdapter.NewsHolder>(
    DiffCallback
) {

    inner class NewsHolder(val newsItemBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(newsItemBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NewsArticleAdapter.NewsHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: NewsArticleAdapter.NewsHolder, position: Int
    ) {
        val item: NewsArticle = getItem(position)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val formatter = SimpleDateFormat("dd / MM / yyyy", Locale.ENGLISH)

        Glide.with(holder.newsItemBinding.imageView)
            .load(item.urlToImage)
            .into(holder.newsItemBinding.imageView)

        holder.newsItemBinding.textTitle.text = item.title
        holder.newsItemBinding.textSource.text = item.source?.name
        holder.newsItemBinding.publishedAt.text = item.publishedAt?.let { s ->
            simpleDateFormat.parse(s)?.let { formatter.format(it) }
        }

        holder.newsItemBinding.cardNews.setOnClickListener {
            item.let { newsArticle ->
                onItemClickListener.onItemClick(newsArticle)
            }
        }
        holder.newsItemBinding.buttonToSave.setOnClickListener {
            onItemClickListener.onSaveButtonClicker(item)
        }
        holder.newsItemBinding.buttonToShare.setOnClickListener {
            onItemClickListener.onShareButtonClicker(item)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NewsArticle>() {
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return newItem.content == oldItem.content
        }
    }

}

