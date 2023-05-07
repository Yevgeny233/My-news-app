package com.myselfproject.mynewsapp.usecases

import com.myselfproject.mynewsapp.network.NetworkService


class NewsRepository constructor(private val networkService: NetworkService) {

    suspend fun getWSJNews() = networkService.getWSJNewsItem()

    suspend fun getBBCNews() = networkService.getBBCNewsItem()

    suspend fun getUANews() = networkService.getUANews()

}