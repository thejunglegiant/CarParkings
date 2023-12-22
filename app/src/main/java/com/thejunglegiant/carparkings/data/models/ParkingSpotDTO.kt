package com.thejunglegiant.carparkings.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParkingSpotDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("price") val price: Float,
    @SerializedName("spots_left") val spotsLeft: Int
) : Parcelable

