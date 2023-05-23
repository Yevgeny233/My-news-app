package com.myselfproject.mynewsapp.di

import com.myselfproject.mynewsapp.databae.ArticleDAO
import com.myselfproject.mynewsapp.models.DataArticle
import javax.inject.Inject


class DataRepository @Inject constructor(private val articleDAO: ArticleDAO) {

    fun getData() = articleDAO.getData()

    suspend fun insertData(dataArticle: DataArticle) = articleDAO.insertData(dataArticle)

    suspend fun deleteData(dataArticle: DataArticle) = articleDAO.deleteData(dataArticle)
}