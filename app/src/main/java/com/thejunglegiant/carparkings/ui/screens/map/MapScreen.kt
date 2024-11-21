package com.thejunglegiant.carparkings.ui.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.thejunglegiant.carparkings.R
import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO
import com.thejunglegiant.carparkings.ui.getActivity
import com.thejunglegiant.carparkings.ui.theme.PrimaryColor
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

                    ParkingSpotDetailsDialogFragment.newInstance(it.spot) { p1 ->
                        viewModel.saveSpot(
                            p1
                        ); onParkingSpotSelected(p1)
                    }
                        .show(activity.supportFragmentManager, ParkingSpotDetailsDialogFragment.TAG)
                }
            }
        }
    }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = PrimaryColor,
                content = {
                    Icon(
                        painter = rememberAsyncImagePainter(R.drawable.ic_filter),
                        contentDescription = null,
                    )
                },
                onClick = {
                    showBottomSheet = true
                }
            )
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "Паркінги") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = rememberVectorPainter(Icons.Filled.Search),
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
            ) {
                MapFilterSheetContent()
            }
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
            ),
        ) {
            state.spots.forEach { spot ->
                MarkerComposable(
                    state = MarkerState(position = LatLng(spot.lat, spot.lng)),
                    title = spot.id.toString(),
                    snippet = "Marker in Kyiv",
                    onClick = {
                        viewModel.selectById(spot.id)

                        false
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = CircleShape,
                            )
                            .background(
                                color = when (spot.spotsLeft) {
                                    in 0..33 -> Color.Red
                                    in 33..66 -> Color.Yellow
                                    else -> Color.Green
                                },
                                shape = CircleShape,
                            )
                    )
                }
            }
        }
    }
}