package com.thejunglegiant.carparkings.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentDTO(
    @SerializedName("time") val time: String,
    @SerializedName("parking_id") val parkingId: Long,
    @SerializedName("price_paid") val price: Int,
) : Parcelable

