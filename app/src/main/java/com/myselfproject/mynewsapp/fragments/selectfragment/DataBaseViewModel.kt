package com.myselfproject.mynewsapp.fragments.selectfragment

import androidx.lifecycle.LiveData
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

    private var _dataBaseNews: LiveData<List<DataArticle>> = repository.getData()
    val dataBaseNews = _dataBaseNews

    fun deleteArticle(dataArticle: DataArticle) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteData(dataArticle)
        updateListNews()
    }

    fun addArticle(dataArticle: DataArticle) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertData(dataArticle)
        updateListNews()
    }

    private fun updateListNews() {
        _dataBaseNews = repository.getData()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
