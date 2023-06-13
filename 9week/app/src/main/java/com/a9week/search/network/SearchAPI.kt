package com.a9week.search.network

import com.a9week.search.models.SearchData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchAPI {

    @GET("v1/search/image.json")
    fun searchImage(
        @Header("X-Naver-Client-Id") clientId : String,
        @Header("X-Naver-Client-Secret") clientSecret : String,
        @Query("query") query : String,
        @Query("display") display : Int
    ): Call<SearchData>
}