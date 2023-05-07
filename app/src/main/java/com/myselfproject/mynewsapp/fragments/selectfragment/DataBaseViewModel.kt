package com.myselfproject.mynewsapp.fragments.selectfragment

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myselfproject.mynewsapp.databae.ArticleDataBase
import com.myselfproject.mynewsapp.models.DataArticle
import com.myselfproject.mynewsapp.usecases.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class DataBaseViewModel(context: Context) : ViewModel() {

    private var repository: DataRepository
    var dataBaseNews: LiveData<List<DataArticle>>

    init {
        val db = ArticleDataBase.getDataBase(context).articleDao()
        repository = DataRepository(db)
        dataBaseNews = repository.getData()

    }

    fun deleteArticle(dataArticle: DataArticle) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteData(dataArticle)
    }

    fun addArticle(dataArticle: DataArticle) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertData(dataArticle)

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

class DataBaseViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DataBaseViewModel::class.java)) {
            DataBaseViewModel(this.context) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}