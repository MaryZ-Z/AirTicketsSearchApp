package com.android.airticketssearchapp.ui.searchresult

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.android.airticketssearchapp.data.AirTicketsSearchRepository
import com.android.airticketssearchapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val airTicketsSearchRepository: AirTicketsSearchRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val from: String = checkNotNull(savedStateHandle[Screen.SearchResult.FROM])
    private val to: String = checkNotNull(savedStateHandle[Screen.SearchResult.TO])
    var localTo by mutableStateOf(to)
        private set
    var localFrom by mutableStateOf(from)
        private set

    fun clearTo() {
        localTo = ""
    }

    fun switchFromTo() {
        val switch = to
        localTo = from
        localFrom = switch
    }
}