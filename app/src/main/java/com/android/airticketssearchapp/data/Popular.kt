package com.android.airticketssearchapp.data

import com.android.airticketssearchapp.R

enum class Popular(val imageResId: Int, val textResId: Int) {
    Istanbul(imageResId = R.drawable.ic_istanbul, textResId = R.string.main_popular_istanbul),
    Sochi(imageResId = R.drawable.ic_sochi, textResId = R.string.main_popular_sochi),
    Phuket(imageResId = R.drawable.ic_phuket, textResId = R.string.main_popular_phuket)
}