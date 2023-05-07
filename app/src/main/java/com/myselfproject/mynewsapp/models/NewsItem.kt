package com.myselfproject.mynewsapp.models

import com.google.gson.annotations.SerializedName

data class NewsItem(
    @SerializedName("status") var status: String?,
    @SerializedName("totalResults") var totalResults: Int?,
    @SerializedName("articles") var articles: ArrayList<NewsArticle>?,
)