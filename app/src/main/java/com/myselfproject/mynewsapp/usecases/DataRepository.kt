package com.myselfproject.mynewsapp.usecases

import com.myselfproject.mynewsapp.databae.ArticleDAO
import com.myselfproject.mynewsapp.models.DataArticle


class DataRepository(private val articleDAO: ArticleDAO) {

    fun getData() = articleDAO.getData()

    suspend fun insertData(dataArticle: DataArticle) = articleDAO.insertData(dataArticle)

    suspend fun deleteData(dataArticle: DataArticle) = articleDAO.deleteData(dataArticle)
}