package com.android.airticketssearchapp.data

import com.android.airticketssearchapp.R

enum class Hints(val iconResId: Int, val textResId: Int) {
    DifficultRoad(iconResId = R.drawable.ic_difficult_road, textResId = R.string.main_hint_difficult_road),
    Somewhere(iconResId = R.drawable.ic_somewhere, textResId = R.string.main_hint_somewhere),
    Weekend(iconResId = R.drawable.ic_weekend, textResId = R.string.main_hint_weekend),
    HotTickets(iconResId = R.drawable.ic_hot_tickets, textResId = R.string.main_hint_hot_tickets)
}