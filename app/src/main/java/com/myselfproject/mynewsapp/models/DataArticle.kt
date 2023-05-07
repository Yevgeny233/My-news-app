package com.myselfproject.mynewsapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_article")
class DataArticle(
    @ColumnInfo("source") var source: String?,
    @ColumnInfo("author") var author: String?,
    @ColumnInfo("title") var title: String?,
    @ColumnInfo("description") var description: String?,
    @ColumnInfo("url") var url: String?,
    @ColumnInfo("urlToImage") var urlToImage: String?,
    @ColumnInfo("publishedAt") var publishedAt: String?,
    @ColumnInfo("content") var content: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    )