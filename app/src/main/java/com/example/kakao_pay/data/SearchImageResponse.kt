package com.example.kakao_pay.data

data class SearchImageResponse(
    val documents: ArrayList<DocumentData>,
    val meta: MetaData
)

data class MetaData(
    val is_end: Boolean,
    val pageable_count: Int,
    val total_count: Int
)
