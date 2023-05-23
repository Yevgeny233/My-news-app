package com.myselfproject.mynewsapp.di

import com.myselfproject.mynewsapp.network.NetworkService
import javax.inject.Inject


class NewsRepository @Inject constructor(private val networkService: NetworkService) {

    suspend fun getWSJNews() = networkService.getWSJNewsItem()

    suspend fun getBBCNews() = networkService.getBBCNewsItem()

    suspend fun getUANews() = networkService.getUANews()

}