package com.thejunglegiant.carparkings.ui.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO
import com.thejunglegiant.carparkings.ui.getActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@Composable
fun MapScreen(
    onParkingSpotSelected: (spot: ParkingSpotDTO) -> Unit,
) {
    val viewModel = getViewModel<MapViewModel>()
    val state = viewModel.state.collectAsState().value
    val activity = LocalContext.current.getActivity() ?: return

    val kyiv = LatLng(50.4504, 30.5245)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(kyiv, 10f)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.actions.collectLatest {
            when (it) {
                is MapScreenActions.OpenSpotDetails -> {
                    val isDialogVisible = activity.supportFragmentManager.findFragmentByTag(
                        ParkingSpotDetailsDialogFragment.TAG
                    ) != null

                    if (isDialogVisible) return@collectLatest

                    ParkingSpotDetailsDialogFragment.newInstance(it.spot) { p1 -> viewModel.saveSpot(p1); onParkingSpotSelected(p1) }
                        .show(activity.supportFragmentManager, ParkingSpotDetailsDialogFragment.TAG)
                }
            }
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        state.spots.forEach { spot ->
            Marker(
                state = MarkerState(position = LatLng(spot.lat, spot.lng)),
                title = spot.id.toString(),
                snippet = "Marker in Kyiv",
                onClick = {
                    viewModel.selectById(spot.id)

                    false
                }
            )
        }
    }
}