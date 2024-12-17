package com.thejunglegiant.carparkings.ui.screens.pay

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thejunglegiant.carparkings.MainActivity
import com.thejunglegiant.carparkings.ui.getActivity
import kotlinx.coroutines.delay

@Composable
fun PayScreen() {
    val activity = LocalContext.current.getActivity() ?: return
    // Timer state
    var currentTime by remember { mutableStateOf(2 * 60 * 60) }
    var timeStr by remember { mutableStateOf("02:00") }

    // Start countdown timer
    LaunchedEffect(currentTime) {
        if(currentTime > 0) {
            delay(1000L)
            currentTime -= 1
            val hours = currentTime / 3600
            val minutes = (currentTime % 3600) / 60
            timeStr = String.format("%02d:%02d", hours, minutes)
        }
    }

    // Screen Layout
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Thank You Text
            Text(
                text = "Дякуємо за оплату!",
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Big Timer
            Text(
                text = timeStr,
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 96.sp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.putExtra(MainActivity.EXTRA_SHOW_NOTIFICATION, false)
                    activity.startActivity(intent)
                    Runtime.getRuntime().exit(0)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Повернутися на головну",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
