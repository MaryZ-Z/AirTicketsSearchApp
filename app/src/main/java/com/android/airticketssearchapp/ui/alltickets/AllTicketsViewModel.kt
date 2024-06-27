package com.android.airticketssearchapp.ui.alltickets

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airticketssearchapp.data.alltickets.AllTickets
import com.android.airticketssearchapp.data.repository.AirTicketsSearchRepository
import com.android.airticketssearchapp.navigation.Screen
import com.android.airticketssearchapp.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTicketsViewModel @Inject constructor(
    private val airTicketsSearchRepository: AirTicketsSearchRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val from: String = checkNotNull(savedStateHandle[Screen.AllTickets.FROM])
    val to: String = checkNotNull(savedStateHandle[Screen.AllTickets.TO])
    val date: String = checkNotNull(savedStateHandle[Screen.AllTickets.DATE])
    val returnDate: String? = savedStateHandle[Screen.AllTickets.RETURN_DATE]
    var state by mutableStateOf<UiState<AllTickets>>(UiState.Loading)
        private set
    private val handler = CoroutineExceptionHandler { _, e ->
        //Log.e("tag", e.stackTraceToString())
    }

    init {
        getAllTickets()
    }

    private fun getAllTickets() {
        viewModelScope.launch(handler) {
            state = UiState.Loading
            state = UiState.Success(airTicketsSearchRepository.getAllTickets())
        }
    }
}