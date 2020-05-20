package com.example.news.main.repo

import androidx.lifecycle.LiveData
import com.example.news.database.MainDao
import com.example.news.model.ArticlesItem
import com.example.news.model.NewsApiResponse
import com.example.news.remote.WebService
import com.example.news.util.helperUtils.AppExecutors
import com.example.news.util.remoteUtils.ApiResponse
import com.example.news.util.remoteUtils.NetworkBoundResource
import com.example.news.util.remoteUtils.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val dao: MainDao,
    private val executor: AppExecutors,
    private val webservice: WebService
) {

    fun fetchNewsItems(): LiveData<Resource<List<ArticlesItem>>> {
        return object : NetworkBoundResource<List<ArticlesItem>, NewsApiResponse>(executor) {
            override fun saveCallResult(item: NewsApiResponse) {
                item.articles?.apply {
                    dao.insertNewsItems(this)
                }
            }

            override fun shouldFetch(data: List<ArticlesItem>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<ArticlesItem>> {
                return dao.fetchNewsItems()
            }

            override fun createCall(): LiveData<ApiResponse<NewsApiResponse>> {
                return webservice.fetchNewsItem()
            }
        }.asLiveData()
    }

}