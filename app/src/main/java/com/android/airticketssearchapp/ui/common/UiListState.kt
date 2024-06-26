package com.android.airticketssearchapp.ui.common

import com.android.airticketssearchapp.data.OffersResponse

sealed interface UiListState<out T> {
    data object Loading : UiListState<Nothing>

    data class Success<T>(val data: OffersResponse) : UiListState<T>

    data object Empty : UiListState<Nothing>
}