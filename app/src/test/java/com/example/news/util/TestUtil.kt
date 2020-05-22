package com.example.news.util

import com.example.news.model.ArticlesItem
import com.example.news.model.NewsApiResponse
import com.example.news.model.Source


object TestUtil {

    fun createArticleResponse(
        count: Int,
        url: String,
        title: String,
        description: String,
        source: String
    ) = NewsApiResponse(
        totalResults = count,
        status = "success",
        articles = createArticles(count, title, url, description, source)
    )

    fun createArticle(url: String, title: String, description: String, source: String) =
        ArticlesItem(
            url = url,
            urlToImage = url,
            title = title,
            publishedAt = null,
            author = null,
            source = Source(name = source, id = null),
            content = null,
            description = description
        )

    fun createArticles(
        count: Int,
        title: String,
        url: String,
        description: String,
        source: String
    ): List<ArticlesItem> {
        return (0 until count).map {
            createArticle(
                "url$it", title, description, source
            )
        }
    }
}
