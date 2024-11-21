package com.thejunglegiant.carparkings.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.thejunglegiant.carparkings.ui.theme.PrimaryColor
import com.thejunglegiant.carparkings.ui.theme.WhiteColor

@Composable
fun ProfileScreen() {
    // Fake user data
    val userName = "Олексій Морозов"
    val userEmail = "oleksii.morozov@gmail.com"
    val userAvatar = "https://avatar.iran.liara.run/public/boy?username=Ash"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // User Avatar
        Image(
            painter = rememberImagePainter(data = userAvatar),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray, CircleShape)
                .padding(4.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Name
        Text(
            text = userName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // User Email
        Text(
            text = userEmail,
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Bio
        ParkingStatsString()

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /*TODO: Edit Profile*/ },
                colors = ButtonDefaults.buttonColors(PrimaryColor)
            ) {
                Text(text = "Редагувати")
            }

            OutlinedButton(
                onClick = { /*TODO: Logout*/ },
            ) {
                Text(text = "Вийти")
            }
        }
    }
}

@Composable
private fun ParkingStatsString() {
    val annotatedText = buildAnnotatedString {
        append("У тебе вже ")

        // Bold "158"
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("158")
        }

        append(" сплачених паркувань.\nТи кращий за ")

        // Bold "98%"
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("98%")
        }

        append(" користувачів, так тримати!")
    }

    Text(
        text = annotatedText,
        fontSize = 16.sp,
        color = Color.DarkGray,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 3,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
