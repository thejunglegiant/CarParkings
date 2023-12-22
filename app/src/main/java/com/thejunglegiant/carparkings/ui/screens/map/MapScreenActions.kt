package com.thejunglegiant.carparkings.ui.screens.map

import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO

sealed class MapScreenActions {

    data class OpenSpotDetails(val spot: ParkingSpotDTO) : MapScreenActions()
}
