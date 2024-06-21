package com.android.airticketssearchapp.restapi

import com.android.airticketssearchapp.data.OffersResponse
import retrofit2.http.GET

interface AirTicketsSearchService {
    @GET("ad9a46ba-276c-4a81-88a6-c068e51cce3a")
    suspend fun getOffers(): OffersResponse
}