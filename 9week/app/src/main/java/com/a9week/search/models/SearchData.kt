package com.a9week.search.models

data class SearchData(
    val items: ArrayList<SearchItems>
)

data class SearchItems(
    val title: String,
    val link: String,
    val thumbnail : String
)
