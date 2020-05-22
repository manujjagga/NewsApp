package com.example.news.main.observer

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.news.main.repo.MainRepository
import com.example.news.model.ArticlesItem
import com.example.news.testing.OpenForTesting
import com.example.news.util.helperUtils.AbsentLiveData
import com.example.news.util.remoteUtils.Resource
import javax.inject.Inject

@OpenForTesting
class DashboardViewModel @Inject constructor(val repo: MainRepository) : ViewModel(), Observable {
    val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    val apiCall = MutableLiveData<String>()

    lateinit var news: LiveData<Resource<List<ArticlesItem>>>


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    fun init() {
        apiCall.postValue("1")
    }

    fun refreshData(){
        apiCall.postValue("2")
    }

    init {
        news = Transformations.switchMap(apiCall) {
            when (apiCall.value) {
                null -> AbsentLiveData.create()
                "1" -> repo.fetchNewsItems()
                else->repo.fetchNewsItems(true)
            }
        }

    }

}