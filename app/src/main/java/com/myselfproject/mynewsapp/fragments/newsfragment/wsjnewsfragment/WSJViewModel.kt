package com.myselfproject.mynewsapp.fragments.newsfragment.wsjnewsfragment

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
class WSJViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    private var _newsWSJItem = MutableLiveData<NewsItem?>()
    val newsWSJItem: LiveData<NewsItem?> = _newsWSJItem

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var _statusLoading = MutableLiveData<Boolean>()
    val statusLoading: LiveData<Boolean> = _statusLoading

    fun getWSJNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    _statusLoading.postValue(true)
                    val response = repository.getWSJNews()
                    if (response.isSuccessful) {
                        _newsWSJItem.postValue(response.body())
                        _statusLoading.postValue(false)

                    } else {
                        _errorMessage.value = response.message()
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
