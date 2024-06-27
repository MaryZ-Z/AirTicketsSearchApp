package com.android.airticketssearchapp.data.alltickets

import com.squareup.moshi.Json

data class HandLuggage(
    @Json(name = "has_hand_luggage")
    val hasHandLuggage: Boolean,
    val size: String?
)
