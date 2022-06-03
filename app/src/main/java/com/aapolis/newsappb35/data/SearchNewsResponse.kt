package com.aapolis.newsappb35.data

data class SearchNewsResponse(
    val news: List<News>,
    val page: Int,
    val status: String
)

