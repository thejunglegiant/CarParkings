package com.thejunglegiant.carparkings.ui.screens.main

import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO
import com.thejunglegiant.carparkings.data.models.PaymentDTO

data class MainScreenState(
    val selectedCarNumber: String? = null,
    val selectedSpot: ParkingSpotDTO? = null,
    val payments: List<PaymentDTO> = emptyList(),
)
