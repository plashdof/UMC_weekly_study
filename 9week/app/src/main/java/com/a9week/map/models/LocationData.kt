package com.a9week.map.models

data class LocationData(
    val documents: ArrayList<Place>          // 검색 결과
)

data class Place(
    val place_name: String,             // 장소명, 업체명
    val address_name: String,           // 전체 지번 주소
    val road_address_name: String,      // 전체 도로명 주소
    val x: String,                      // X 좌표값 혹은 longitude
    val y: String,                      // Y 좌표값 혹은 latitude
)
