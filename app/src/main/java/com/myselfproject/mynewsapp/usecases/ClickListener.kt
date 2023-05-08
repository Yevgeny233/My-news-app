package com.myselfproject.mynewsapp.usecases

import com.myselfproject.mynewsapp.models.DataArticle
import com.myselfproject.mynewsapp.models.NewsArticle


interface OnItemClickListener {
    fun onItemClick(newsArticle: NewsArticle)
    fun onSaveButtonClicker(newsArticle: NewsArticle)
    fun onShareButtonClicker(newsArticle: NewsArticle)

}

interface OnDataArticleClicker {
    fun onArticleClicker(dataArticle: DataArticle)
    fun deleteButtonClick(dataArticle: DataArticle)
    fun shareButtonClick(dataArticle: DataArticle)
}