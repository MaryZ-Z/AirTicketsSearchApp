package com.android.airticketssearchapp.data.repository

import com.android.airticketssearchapp.restapi.AirTicketsSearchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AirTicketsSearchRepository @Inject constructor(
    private val airTicketsSearchService: AirTicketsSearchService
) {
    suspend fun getOffers() = airTicketsSearchService.getOffers()

    suspend fun getSearchResults() = airTicketsSearchService.getSearchResults()

    suspend fun getAllTickets() = airTicketsSearchService.getAllTickets()
}