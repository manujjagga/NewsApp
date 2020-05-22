package com.example.news.remote

import androidx.lifecycle.LiveData
import com.example.news.model.NewsApiResponse
import com.example.news.util.helperUtils.AppConfig
import com.example.news.util.remoteUtils.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WebService {

    @GET("v2/top-headlines")
    fun fetchNewsItem(
        @Query("country") country: String = "us",
        @Query("category") category: String = "business",
        @Query("apiKey") apiKey: String = AppConfig.API_KEY
    ):LiveData<ApiResponse<NewsApiResponse>>

}