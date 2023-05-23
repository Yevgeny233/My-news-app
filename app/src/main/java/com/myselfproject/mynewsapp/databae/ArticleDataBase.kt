package com.myselfproject.mynewsapp.databae

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myselfproject.mynewsapp.models.DataArticle

@Database(entities = [DataArticle::class], version = 1)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun articleDao(): ArticleDAO
}
