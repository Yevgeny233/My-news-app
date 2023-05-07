package com.myselfproject.mynewsapp.fragments.newsfragment.bbcnewsfragment

import androidx.lifecycle.*
import com.myselfproject.mynewsapp.usecases.NewsRepository
import com.myselfproject.mynewsapp.models.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BBCViewModel constructor(private val repository: NewsRepository) : ViewModel() {

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

class BBCViewModelFactory(
    private val repository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BBCViewModel::class.java)) {
            BBCViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}