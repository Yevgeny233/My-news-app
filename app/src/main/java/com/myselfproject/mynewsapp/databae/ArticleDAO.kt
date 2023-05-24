package com.myselfproject.mynewsapp.databae

import androidx.room.*
import com.myselfproject.mynewsapp.models.DataArticle
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(dataArticle: DataArticle)

    @Query("SELECT * FROM data_article")
    fun getData(): Flow<List<DataArticle>>

    @Delete
    suspend fun deleteData(dataArticle: DataArticle)

}