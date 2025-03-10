package com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity

import java.util.Date

data class Sports(
    val sportId: String,
    val sportName: String,
    val events: List<Event>,
    val isFavorite: Boolean = false
)
