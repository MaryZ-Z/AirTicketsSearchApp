package com.android.airticketssearchapp.restapi

import com.android.airticketssearchapp.data.OffersResponse
import com.android.airticketssearchapp.data.TicketsOffers
import com.android.airticketssearchapp.data.alltickets.AllTickets
import retrofit2.http.GET

interface AirTicketsSearchService {
    @GET("ad9a46ba-276c-4a81-88a6-c068e51cce3a")
    suspend fun getOffers(): OffersResponse

    @GET("38b5205d-1a3d-4c2f-9d77-2f9d1ef01a4a")
    suspend fun getSearchResults(): TicketsOffers

    @GET("c0464573-5a13-45c9-89f8-717436748b69")
    suspend fun getAllTickets(): AllTickets
}