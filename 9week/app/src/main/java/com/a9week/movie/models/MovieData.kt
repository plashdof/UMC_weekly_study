package com.a9week.movie.models

data class MovieData(
    val boxOfficeResult : MovieBoxDetail
)

data class MovieBoxDetail(
    val boxofficeType : String,
    val showRange : String,
    val dailyBoxOfficeList : ArrayList<MovieBoxRankings>
)


data class MovieBoxRankings(
    val rank : String,
    val movieNm : String,
    val audiAcc : String
)

