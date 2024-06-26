package com.android.airticketssearchapp.ui.searchresult

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airticketssearchapp.data.TicketsOffers
import com.android.airticketssearchapp.data.repository.AirTicketsSearchRepository
import com.android.airticketssearchapp.navigation.Screen
import com.android.airticketssearchapp.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val airTicketsSearchRepository: AirTicketsSearchRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val from: String = checkNotNull(savedStateHandle[Screen.SearchResult.FROM])
    private val to: String = checkNotNull(savedStateHandle[Screen.SearchResult.TO])
    var state by mutableStateOf<UiState<TicketsOffers>>(UiState.Loading)
        private set
    var localTo by mutableStateOf(to)
        private set
    var localFrom by mutableStateOf(from)
        private set
    var date by mutableStateOf<LocalDate>(LocalDate.now())
        private set
    var returnDate by mutableStateOf<LocalDate?>(null)
        private set
    var isDatePickerDialogShowing by mutableStateOf(false)
        private set
    var isReturnDatePickerDialogShowing by mutableStateOf(false)
        private set
    private val handler = CoroutineExceptionHandler { _, _ ->
        // TODO
    }

    init {
        getSearchResults()
    }

    fun clearTo() {
        localTo = ""
    }

    fun switchFromTo() {
        val switch = localTo
        localTo = localFrom
        localFrom = switch
    }

    fun updateDate(date: Long?) {
        if (date != null) this.date = LocalDate.ofEpochDay(Duration.ofMillis(date).toDays())
        hideDatePickerDialog()
    }

    fun updateReturnDate(returnDate: Long?) {
        if (returnDate != null) {
            this.returnDate = LocalDate.ofEpochDay(Duration.ofMillis(returnDate).toDays())
        }
        hideReturnDatePickerDialog()
    }

    fun showDatePickerDialog() {
        isDatePickerDialogShowing = true
    }

    fun hideDatePickerDialog() {
        isDatePickerDialogShowing = false
    }

    fun showReturnDatePickerDialog() {
        isReturnDatePickerDialogShowing = true
    }

    fun hideReturnDatePickerDialog() {
        isReturnDatePickerDialogShowing = false
    }

    private fun getSearchResults() {
        viewModelScope.launch(handler) {
            state = UiState.Loading
            state = UiState.Success(airTicketsSearchRepository.getSearchResults())
        }
    }
}