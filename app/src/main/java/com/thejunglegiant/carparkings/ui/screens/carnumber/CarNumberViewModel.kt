package com.thejunglegiant.carparkings.ui.screens.carnumber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejunglegiant.carparkings.data.api.ApiService
import com.thejunglegiant.carparkings.data.api.CarNumberData
import com.thejunglegiant.carparkings.data.api.ParkingSpotData
import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarNumberViewModel(
    private val api: ApiService,
) : ViewModel() {

    private val _actions = MutableSharedFlow<CarNumberScreenActions>()
    val actions = _actions.asSharedFlow()

    fun saveNumber(number: String) {
        viewModelScope.launch {
            val data = CarNumberData(number)
            try {
                api.saveCarNumber(data)
            } catch (e: Exception) {
                _actions.emit(CarNumberScreenActions.ErrorNumber)
            }
            _actions.emit(CarNumberScreenActions.GoBack)
        }
    }
}