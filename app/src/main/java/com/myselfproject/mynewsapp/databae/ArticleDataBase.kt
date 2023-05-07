package com.myselfproject.mynewsapp.databae

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myselfproject.mynewsapp.models.DataArticle

@Database(entities = [DataArticle::class], version = 1)
abstract class ArticleDataBase : RoomDatabase() {

    abstract fun articleDao(): ArticleDAO

    companion object {

        @Volatile
        private var INSTANCE: ArticleDataBase? = null

        fun getDataBase(context: Context): ArticleDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ArticleDataBase::class.java,
                        "article_base"
                    )
                        .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
