package com.android.airticketssearchapp.ui.common

import androidx.annotation.StringRes
import com.android.airticketssearchapp.R

sealed class UiError(
    override val message: String? = null,
    @StringRes val messageRes: Int = R.string.unknown_error_message
) : Throwable(message) {

    data object RouteIsNull : UiError(messageRes = R.string.route_is_null_error_message)

    data class Common(override val message: String? = null) : UiError()
}