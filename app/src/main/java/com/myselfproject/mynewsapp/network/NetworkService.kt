package com.myselfproject.mynewsapp.network

import com.myselfproject.mynewsapp.models.NewsItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface NetworkService {
    @GET(GET_WALL_STREET_JOURNAL + API_KEY)
    suspend fun getWSJNewsItem(): Response<NewsItem>

    @GET(GET_BBC_NEWS + API_KEY)
    suspend fun getBBCNewsItem(): Response<NewsItem>

    @GET(GET_UA_NEWS + API_KEY)
    suspend fun getUANews(): Response<NewsItem>

    companion object {
        private var networkService: NetworkService? = null

        fun getInstance(): NetworkService {
            if (networkService == null) {
                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL).build()
                networkService = retrofit.create(NetworkService::class.java)
            }
            return networkService!!
        }

    }

}