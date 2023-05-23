package com.myselfproject.mynewsapp.fragments.newsfragment.bbcnewsfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myselfproject.mynewsapp.di.NewsRepository
import com.myselfproject.mynewsapp.models.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BBCViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private var _bbcNewsItem = MutableLiveData<NewsItem>()
    val bbcNewsItem: LiveData<NewsItem> = _bbcNewsItem

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var _statusLoading = MutableLiveData<Boolean>()
    val statusLoading: LiveData<Boolean> = _statusLoading

    fun getBBCNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    _statusLoading.postValue(true)
                    val response = repository.getBBCNews()
                    if (response.isSuccessful) {
                        _bbcNewsItem.postValue(response.body())
                        _statusLoading.postValue(false)
                    } else {
                        _errorMessage.postValue(response.message())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
