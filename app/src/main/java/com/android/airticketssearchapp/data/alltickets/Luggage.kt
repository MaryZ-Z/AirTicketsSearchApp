package com.android.airticketssearchapp.data.alltickets

import com.android.airticketssearchapp.data.Price
import com.squareup.moshi.Json

data class Luggage(
    @Json(name = "has_luggage")
    val hasLuggage: Boolean,
    val price: Price?
)
