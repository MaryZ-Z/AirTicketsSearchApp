package com.android.airticketssearchapp.data.alltickets

import com.android.airticketssearchapp.data.Price
import com.squareup.moshi.Json
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit

data class Ticket(
    val id: Int,
    val badge: String?,
    val price: Price,
    @Json(name = "provider_name")
    val providerName: String,
    val company: String,
    val departure: Flight,
    val arrival: Flight,
    @Json(name = "has_transfer")
    val hasTransfer: Boolean,
    @Json(name = "has_visa_transfer")
    val hasVisaTransfer: Boolean,
    val luggage: Luggage,
    @Json(name = "hand_luggage")
    val handLuggage: HandLuggage,
    @Json(name = "is_returnable")
    val isReturnable: Boolean,
    @Json(name = "is_exchangable")
    val isExchangeable: Boolean,
) {
    private val arrivalDateTime: LocalDateTime = LocalDateTime.parse(arrival.date)
    val arrivalTime: LocalTime = arrivalDateTime.toLocalTime()
    private val departureDateTime: LocalDateTime = LocalDateTime.parse(departure.date)
    val departureTime: LocalTime = departureDateTime.toLocalTime()

    private val timeOnWay: Long = Duration.between(departureDateTime, arrivalDateTime).toMillis()
    private val timerHours: Long = TimeUnit.MILLISECONDS.toHours(timeOnWay) % 24
    private val timerMinutes: Long = TimeUnit.MILLISECONDS.toMinutes(timeOnWay) % 6

    val timeOnWayString = if (timerMinutes > 0) "$timerHours.$timerMinutes" else "$timerHours"
}