package com.thejunglegiant.carparkings.ui.screens.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO
import com.thejunglegiant.carparkings.ui.theme.BlackColor
import com.thejunglegiant.carparkings.ui.theme.BlackShade300Color
import com.thejunglegiant.carparkings.ui.theme.PrimaryColor
import com.thejunglegiant.carparkings.ui.theme.PrimaryDarkColor

class ParkingSpotDetailsDialogFragment : BottomSheetDialogFragment() {

    private var selectSpot: (spot: ParkingSpotDTO) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val spot = arguments?.getParcelable<ParkingSpotDTO>(EXTRA_SPOT)
        spot ?: dismiss()

        return ComposeView(requireContext()).apply {
            setContent {
                MyComposeContent(spot!!)
            }
        }
    }

    @Composable
    fun MyComposeContent(spot: ParkingSpotDTO) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = PrimaryDarkColor,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "№${spot.id}")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Вільних місць: ${spot.spotsLeft}")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = BlackShade300Color,
                ),
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "${spot.price}\$",
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "За годину",
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Spacer(modifier = Modifier
                        .height(16.dp)
                        .width(1.dp)
                        .background(BlackColor)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "${spot.price / 2}\$",
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "За 30хв",
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    selectSpot(spot)
                    dismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                ),
            ) {
                Text(text = "Обрати парковку")
            }
        }
    }

    companion object {
        private const val EXTRA_SPOT = "EXTRA_SPOT"
        const val TAG = "ParkingSpotDetailsDialogFragment"

        fun newInstance(spot: ParkingSpotDTO, selectSpot: (spot: ParkingSpotDTO) -> Unit): ParkingSpotDetailsDialogFragment =
            ParkingSpotDetailsDialogFragment().apply {
                this.selectSpot = selectSpot
                arguments = Bundle().apply { putParcelable(EXTRA_SPOT, spot) }
            }
    }
}
