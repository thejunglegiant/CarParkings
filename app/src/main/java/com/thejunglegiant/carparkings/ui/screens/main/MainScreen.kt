package com.thejunglegiant.carparkings.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thejunglegiant.carparkings.data.models.PaymentDTO
import com.thejunglegiant.carparkings.ui.components.CpTabLayoutWithPager
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
                title = { Text(text = "Паркування", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { onProfileClicked() }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
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
                            modifier = Modifier.fillMaxSize(),
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
                            modifier = Modifier.fillMaxSize(),
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
            .padding(16.dp)
    ) {
        Text(
            text = "Автомобіль",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            state.selectedCarNumber?.let { number ->
                item {
                    Card(
                        modifier = Modifier.height(80.dp),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(1.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        onClick = onAddNumberClicked
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Моя автівка",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = number.uppercase(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.size(80.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(1.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    onClick = onAddNumberClicked
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Car",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Місце паркування",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (state.selectedSpot == null) {
            ParkingSpotCard(
                title = "Обрати паркінг",
                subtitle = "Номер паркінгу має відповідати номеру на паркувальному знаці",
                onClick = onChooseParkingClicked
            )
        } else {
            ParkingSpotCard(
                title = "Паркінг №${state.selectedSpot.id}",
                subtitle = "Паркінг обрано, переходьте до оплати",
                onClick = onChooseParkingClicked,
                borderColor = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (state.selectedSpot != null && state.selectedCarNumber != null) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onPayClicked,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Оплатити", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ParkingSpotCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    borderColor: Color = MaterialTheme.colorScheme.outline
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(1.dp),
        border = BorderStroke(1.dp, borderColor),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun PaymentsTabContent(
    modifier: Modifier = Modifier,
    payments: List<PaymentDTO>
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(payments.sortedByDescending { it.time }) { payment ->
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(1.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = payment.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Паркінг №${payment.parkingId}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${payment.price}\$",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
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
