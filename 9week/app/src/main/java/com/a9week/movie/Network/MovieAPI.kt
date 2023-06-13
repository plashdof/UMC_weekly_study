package com.a9week.movie.Network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("boxoffice/searchDailyBoxOfficeList.json")
    fun getBoxoffice(
        @Query("key") key : String,
        @Query("targetDt") targetDt : String,
        @Query("repNationCd") rep : String?=null
    ) : Call<MovieData>

}