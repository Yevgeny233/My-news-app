package com.myselfproject.mynewsapp.databae

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myselfproject.mynewsapp.models.DataArticle


@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(dataArticle: DataArticle)

    @Query("SELECT * FROM data_article")
    fun getData(): LiveData<List<DataArticle>>

    @Delete
    suspend fun deleteData(dataArticle: DataArticle)

}