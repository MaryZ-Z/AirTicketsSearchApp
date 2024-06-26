package com.android.airticketssearchapp.data

import com.squareup.moshi.Json

data class TicketsOffers(
    @Json(name = "tickets_offers")
    val ticketsOffers: List<SearchResponse>
)
