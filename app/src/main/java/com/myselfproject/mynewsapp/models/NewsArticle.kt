package com.myselfproject.mynewsapp.models

import com.google.gson.annotations.SerializedName


data class NewsArticle(
    @SerializedName("source") var source: Source?,
    @SerializedName("author") var author: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("url") var url: String,
    @SerializedName("urlToImage") var urlToImage: String?,
    @SerializedName("publishedAt") var publishedAt: String?,
    @SerializedName("content") var content: String?
)