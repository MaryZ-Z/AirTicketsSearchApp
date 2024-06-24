package com.android.airticketssearchapp.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airticketssearchapp.data.repository.AirTicketsSearchRepository
import com.android.airticketssearchapp.data.OffersResponse
import com.android.airticketssearchapp.data.repository.DataStoreRepository
import com.android.airticketssearchapp.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val airTicketsSearchRepository: AirTicketsSearchRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val savedFrom = dataStoreRepository.getFrom() ?: ""
    var state by mutableStateOf<UiState<OffersResponse>>(UiState.Loading)
        private set
    var from by mutableStateOf(savedFrom)
        private set
    var to by mutableStateOf("")
        private set
    var showSearchBottomSheet by mutableStateOf(false)
        private set
    private val ignoredRegex = Regex( //костыль, но пока лучше идей нет
        "[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789]"
    )
    private val handler = CoroutineExceptionHandler { _, _ ->
        // TODO infinite loading when cache is empty and request was unsuccessful
    }

    init {
        getOffers()
    }

    fun updateFrom(from: String) {
        this.from = if (!from.contains(ignoredRegex)) {
            from.filter { it.isLetter() }
        } else {
            this.from.filter { it.isLetter() }
        }
        viewModelScope.launch {
            dataStoreRepository.saveLastFrom(from)
        }
    }

    fun updateTo(to: String) {
        this.to = if (!to.contains(ignoredRegex)) {
            to.filter { it.isLetter() }
        } else {
            this.to.filter { it.isLetter() }
        }
    }

    fun clearTo() {
        to = ""
    }

    private fun getOffers() {
        viewModelScope.launch(handler) {
            state = UiState.Loading
            val items = airTicketsSearchRepository.getOffers()
            state = UiState.Success(items)
        }
    }

    fun showSearchBottomSheet() {
        showSearchBottomSheet = true
    }

    fun hideSearchBottomSheet() {
        showSearchBottomSheet = false
    }
}