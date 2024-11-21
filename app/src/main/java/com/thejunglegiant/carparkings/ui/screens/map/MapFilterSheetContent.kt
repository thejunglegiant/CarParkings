package com.thejunglegiant.carparkings.ui.screens.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MapFilterSheetContent(modifier: Modifier = Modifier) {
    var regularSpotChecked by remember { mutableStateOf(false) }
    var invalidSpotChecked by remember { mutableStateOf(false) }
    var extendedSpotChecked by remember { mutableStateOf(false) }
    var electricSpotChecked by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableFloatStateOf(.5f) }

    Column(
        modifier = modifier
            .navigationBarsPadding()
            .padding(16.dp),
    ) {
        Text(text = "Тип паркомісця", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .clickable(
                    role = Role.Checkbox,
                    onClick = {
                        regularSpotChecked = !regularSpotChecked
                    },
                )
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Checkbox(
                checked = regularSpotChecked,
                onCheckedChange = null,
            )
            Text(text = "Звичайне")
        }
        Row(
            modifier = Modifier
                .clickable(
                    role = Role.Checkbox,
                    onClick = {
                        invalidSpotChecked = !invalidSpotChecked
                    },
                )
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Checkbox(
                checked = invalidSpotChecked,
                onCheckedChange = null
            )
            Text(text = "Для малобільних")
        }
        Row(
            modifier = Modifier
                .clickable(
                    role = Role.Checkbox,
                    onClick = {
                        extendedSpotChecked = !extendedSpotChecked
                    },
                )
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Checkbox(
                checked = extendedSpotChecked,
                onCheckedChange = null,
            )
            Text(text = "Для габаритного транспорту")
        }
        Row(
            modifier = Modifier
                .clickable(
                    role = Role.Checkbox,
                    onClick = {
                        electricSpotChecked = !electricSpotChecked
                    },
                )
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Checkbox(
                checked = electricSpotChecked,
                onCheckedChange = null,
            )
            Text(text = "Для електромобілю")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            Text(text = "Вартість", fontWeight = FontWeight.Bold)
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it }
            )
            Text(text = "до: ${(sliderPosition * 100).toInt()}грн")
        }
    }
}