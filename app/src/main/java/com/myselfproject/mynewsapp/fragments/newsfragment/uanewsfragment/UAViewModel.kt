package com.myselfproject.mynewsapp.fragments.newsfragment.uanewsfragment

import androidx.lifecycle.*
import com.myselfproject.mynewsapp.usecases.NewsRepository
import com.myselfproject.mynewsapp.models.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UAViewModel constructor(private val repository: NewsRepository) : ViewModel() {

    private var _uaNewsItem = MutableLiveData<NewsItem>()
    val uaNewsItem: LiveData<NewsItem> = _uaNewsItem

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var _statusLoading = MutableLiveData<Boolean>()
    val statusLoading: LiveData<Boolean> = _statusLoading

    fun getUANews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    _statusLoading.postValue(true)
                    val response = repository.getUANews()
                    if (response.isSuccessful) {
                        _uaNewsItem.postValue(response.body())
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

class UAViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UAViewModel::class.java)) {
            UAViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
