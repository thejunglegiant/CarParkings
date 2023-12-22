package com.thejunglegiant.carparkings.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejunglegiant.carparkings.data.api.ApiService
import com.thejunglegiant.carparkings.data.api.ParkingSpotData
import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(MapScreenState())
    val state = _state.asStateFlow()

    private val _actions = MutableSharedFlow<MapScreenActions>()
    val actions = _actions.asSharedFlow()

    init {
        viewModelScope.launch {
            val locations = api.getLocations()
            _state.emit(
                _state.value.copy(
                    spots = locations,
                )
            )
        }
    }

    fun selectById(id: Long) {
        viewModelScope.launch {
            val selectedSpot = _state.value.spots.find { it.id == id }

            selectedSpot?.let {
                _actions.emit(MapScreenActions.OpenSpotDetails(it))
            }
        }
    }

    fun saveSpot(spot: ParkingSpotDTO) {
        viewModelScope.launch {
            val data = ParkingSpotData(id = spot.id)
            api.saveParkingSpot(data)
        }
    }
}