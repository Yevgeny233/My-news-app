package com.myselfproject.mynewsapp.fragments.selectfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myselfproject.mynewsapp.di.DataRepository
import com.myselfproject.mynewsapp.models.DataArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataBaseViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    private var _dataBaseNews = MutableLiveData<List<DataArticle>>()
    val dataBaseNews = _dataBaseNews

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getData().collect { dataArticleList ->
                _dataBaseNews.postValue(dataArticleList.toList())
            }
        }
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
