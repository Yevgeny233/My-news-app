package com.myselfproject.mynewsapp.di

import android.content.Context
import androidx.room.Room
import com.myselfproject.mynewsapp.databae.ArticleDAO
import com.myselfproject.mynewsapp.databae.ArticleDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object DatabaseModule {
    @Provides
    @Singleton
    fun provideDataBaseInstance(@ApplicationContext context: Context): ArticleDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            ArticleDataBase::class.java,
            "article_base"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDataBaseDao(articleDataBase: ArticleDataBase): ArticleDAO {
        return articleDataBase.articleDao()
    }
}