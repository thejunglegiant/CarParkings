package com.thejunglegiant.carparkings.data.api

import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO
import com.thejunglegiant.carparkings.data.models.PaymentDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("locations")
    suspend fun getLocations(): List<ParkingSpotDTO>

    @POST("saveParkingSpot")
    suspend fun saveParkingSpot(@Body parkingSpot: ParkingSpotData): ServerResponse

    @POST("saveCarNumber")
    suspend fun saveCarNumber(@Body carNumberData: CarNumberData): ServerResponse

    @POST("pay")
    suspend fun pay(@Body payData: PayData): ServerResponse

    @GET("getPayments")
    suspend fun getPayments(): List<PaymentDTO>
}

data class ParkingSpotData(val id: Long)
data class ServerResponse(val success: Boolean, val message: String)

data class CarNumberData(val carNumber: String)
data class PayData(val parkingId: Long, val pricePaid: Int)
