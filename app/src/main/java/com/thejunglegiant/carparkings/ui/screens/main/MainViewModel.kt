package com.thejunglegiant.carparkings.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.thejunglegiant.carparkings.data.api.ApiService
import com.thejunglegiant.carparkings.data.api.PayData
import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val api: ApiService,
): ViewModel() {

    private lateinit var socket: Socket
    private val gson by lazy {
        Gson()
    }

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    init {
        connectSocket()
    }

    private fun connectSocket() {
        try {
            socket = IO.socket("http://10.0.2.2:3000")
            socket.connect()

            socket.on(SELECTED_PARKING_EVENT) { args ->
                val spot = gson.fromJson("""${args[0]}""", ParkingSpotDTO::class.java)
                viewModelScope.launch {
                    _state.emit(
                        _state.value.copy(
                            selectedSpot = spot,
                        )
                    )
                }
            }
            socket.on(SELECTED_NUMBER_EVENT) { args ->
                val number = args[0].toString()
                viewModelScope.launch {
                    _state.emit(
                        _state.value.copy(
                            selectedCarNumber = number,
                        )
                    )
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pay() {
        viewModelScope.launch {
            val data = PayData(
                parkingId = _state.value.selectedSpot?.id ?: 0L,
                pricePaid = _state.value.selectedSpot?.price?.toInt() ?: 0,
            )
            api.pay(data)
        }
    }

    fun refreshPayments() {
        viewModelScope.launch {
            val payments = api.getPayments()
            _state.emit(
                _state.value.copy(
                    payments = payments,
                )
            )
        }
    }

    companion object {
        private const val SELECTED_PARKING_EVENT = "SELECTED_PARKING"
        private const val SELECTED_NUMBER_EVENT = "SELECTED_NUMBER"
    }
}