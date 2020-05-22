package com.example.news.model

import com.google.gson.annotations.SerializedName

data class NewsApiResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int = 0,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem> = emptyList(),

	@field:SerializedName("status")
	val status: String = ""
)
