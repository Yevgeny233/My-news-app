package com.myselfproject.mynewsapp.network

import com.myselfproject.mynewsapp.API_KEY
import com.myselfproject.mynewsapp.GET_BBC_NEWS
import com.myselfproject.mynewsapp.GET_UA_NEWS
import com.myselfproject.mynewsapp.GET_WALL_STREET_JOURNAL
import com.myselfproject.mynewsapp.models.NewsItem
import retrofit2.Response
import retrofit2.http.GET


interface NetworkService {
    @GET(GET_WALL_STREET_JOURNAL + API_KEY)
    suspend fun getWSJNewsItem(): Response<NewsItem>

    @GET(GET_BBC_NEWS + API_KEY)
    suspend fun getBBCNewsItem(): Response<NewsItem>

    @GET(GET_UA_NEWS + API_KEY)
    suspend fun getUANews(): Response<NewsItem>

}