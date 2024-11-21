package com.thejunglegiant.carparkings.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO
import com.thejunglegiant.carparkings.data.models.PaymentDTO
import com.thejunglegiant.carparkings.ui.components.CpTabLayoutWithPager
import com.thejunglegiant.carparkings.ui.theme.BlackShade900Color
import com.thejunglegiant.carparkings.ui.theme.PrimaryColor
import com.thejunglegiant.carparkings.ui.theme.WhiteColor
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    onChooseParkingClicked: () -> Unit,
    onPayClicked: () -> Unit,
    onAddNumberClicked: () -> Unit,
    onProfileClicked: () -> Unit,
) {
    val viewModel = koinViewModel<MainViewModel>()
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Паркування")
                },
                actions = {
                    IconButton(onClick = {
                        onProfileClicked()
                    }) {
                        Icon(
                            painter = rememberVectorPainter(Icons.Default.Person),
                            contentDescription = null,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WhiteColor,
                ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            CpTabLayoutWithPager(
                tabs = listOf(
                    MainScreenTabs.PARKING.tabName,
                    MainScreenTabs.PAYMENTS.tabName,
                ),
                onTabClicked = { index ->
                    if (index == MainScreenTabs.PAYMENTS.index) {
                        viewModel.refreshPayments()
                    }
                },
            ) { index ->
                when (index) {
                    MainScreenTabs.PARKING.index -> {
                        ParkingTabContent(
                            modifier = Modifier
                                .fillMaxSize(),
                            state = state,
                            onChooseParkingClicked = onChooseParkingClicked,
                            onPayClicked = {
                                viewModel.pay()
                                onPayClicked()
                            },
                            onAddNumberClicked = onAddNumberClicked,
                        )
                    }

                    MainScreenTabs.PAYMENTS.index -> {
                        PaymentsTabContent(
                            modifier = Modifier
                                .fillMaxSize(),
                            payments = state.payments,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ParkingTabContent(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    onChooseParkingClicked: () -> Unit,
    onPayClicked: () -> Unit,
    onAddNumberClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(16.dp),
    ) {
        Text(text = "Автомобіль")
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.selectedCarNumber?.let { number ->
                item {
                    Card(
                        modifier = Modifier
                            .height(64.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 1.dp
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = PrimaryColor,
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = WhiteColor,
                        ),
                        onClick = onAddNumberClicked
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "Моя автівка",
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = number.uppercase(),
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.size(64.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = BlackShade900Color,
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = WhiteColor,
                    ),
                    onClick = onAddNumberClicked
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Місце паркування")
        Spacer(modifier = Modifier.height(12.dp))
        if (state.selectedSpot == null) {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = BlackShade900Color,
                ),
                colors = CardDefaults.cardColors(
                    containerColor = WhiteColor,
                ),
                onClick = onChooseParkingClicked,
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                    ) {
                        Text(
                            text = "Обрати паркінг",
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Номер паркінгу має відповідати номеру на паркувальному знаці",
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                    )
                }
            }
        } else {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = PrimaryColor,
                ),
                colors = CardDefaults.cardColors(
                    containerColor = WhiteColor,
                ),
                onClick = onChooseParkingClicked,
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                    ) {
                        Text(
                            text = "Паркінг №${state.selectedSpot.id}",
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Паркінг обрано, переходьте до оплати",
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (state.selectedSpot != null && state.selectedCarNumber != null) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onPayClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                ),
            ) {
                Text(text = "Оплатити")
            }
        }
    }
}

@Composable
private fun PaymentsTabContent(
    modifier: Modifier = Modifier,
    payments: List<PaymentDTO>,
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(payments) { item ->
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = WhiteColor,
                ),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    Text(
                        text = item.time,
                        fontWeight = FontWeight.Thin,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Паркінг №${item.parkingId}",
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${item.price}\$",
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

enum class MainScreenTabs(val index: Int, val tabName: String) {
    PARKING(0, "Паркування"),
    PAYMENTS(1, "Оплати"),
}
