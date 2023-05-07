package com.myselfproject.mynewsapp.usecases

import com.myselfproject.mynewsapp.models.DataArticle
import com.myselfproject.mynewsapp.models.NewsArticle


interface OnItemClickListener {
    fun onItemClick(newsArticle: NewsArticle)
}

interface OnSaveButtonClicker {
    fun onSaveButtonClicker(newsArticle: NewsArticle)
}

interface OnShareButtonClicker {
    fun onShareButtonClicker(newsArticle: NewsArticle)
}

interface OnButtonClicker {
    fun deleteButtonClick(dataArticle: DataArticle)
    fun shareButtonClick(dataArticle: DataArticle)
}

interface OnArticleClicker {
    fun onArticleClicker(dataArticle: DataArticle)
}