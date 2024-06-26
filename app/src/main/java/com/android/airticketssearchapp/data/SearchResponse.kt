package com.android.airticketssearchapp.data

import com.squareup.moshi.Json

data class SearchResponse(
    val id: Int,
    val title: String,
    @Json(name = "time_range")
    val timeRange: List<String>,
    val price: Price
)
