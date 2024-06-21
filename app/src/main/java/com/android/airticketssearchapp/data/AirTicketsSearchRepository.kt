package com.android.airticketssearchapp.data

import com.android.airticketssearchapp.restapi.AirTicketsSearchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AirTicketsSearchRepository @Inject constructor(
    private val airTicketsSearchService: AirTicketsSearchService
) {
    suspend fun getOffers() = airTicketsSearchService.getOffers()
}