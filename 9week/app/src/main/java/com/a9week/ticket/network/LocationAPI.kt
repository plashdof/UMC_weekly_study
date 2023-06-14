package com.a9week.ticket.network

import com.a9week.ticket.models.LocationData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LocationAPI {

    @GET("v2/local/search/keyword.json")
    fun getSearchKeyword(
        @Header("Authorization") key: String,     // 카카오 API 인증키 [필수]
        @Query("query") query: String             // 검색을 원하는 질의어 [필수]
        // 매개변수 추가 가능
        // @Query("category_group_code") category: String

    ): Call<LocationData>
}