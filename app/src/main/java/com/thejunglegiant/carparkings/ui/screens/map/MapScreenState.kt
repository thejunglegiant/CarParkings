package com.thejunglegiant.carparkings.ui.screens.map

import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO

data class MapScreenState(
    val spots: List<ParkingSpotDTO> = emptyList(),
    val selectedSpot: ParkingSpotDTO? = null,
)
