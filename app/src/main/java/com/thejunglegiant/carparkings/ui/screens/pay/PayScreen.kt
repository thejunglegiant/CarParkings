package com.thejunglegiant.carparkings.ui.screens.pay

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.thejunglegiant.carparkings.MainActivity
import com.thejunglegiant.carparkings.ui.getActivity
import com.thejunglegiant.carparkings.ui.theme.PrimaryColor

@Composable
fun PayScreen() {
    val activity = LocalContext.current.getActivity() ?: return

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Оплата пройшла успішно!")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val intent = Intent(activity, MainActivity::class.java)
                    activity.startActivity(intent)
                    Runtime.getRuntime().exit(0)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                ),
            ) {
                Text(text = "Головна")
            }
        }
    }
}