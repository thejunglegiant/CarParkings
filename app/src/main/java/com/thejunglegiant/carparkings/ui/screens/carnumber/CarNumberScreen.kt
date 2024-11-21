package com.thejunglegiant.carparkings.ui.screens.carnumber

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.thejunglegiant.carparkings.ui.getActivity
import com.thejunglegiant.carparkings.ui.theme.PrimaryColor
import com.thejunglegiant.carparkings.ui.theme.WhiteColor
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarNumberScreen(
    onBackClicked: () -> Unit,
) {
    val viewModel = getViewModel<CarNumberViewModel>()
    val activity = LocalContext.current.getActivity() ?: return

    LaunchedEffect(key1 = Unit) {
        viewModel.actions.collect {
            when (it) {
                CarNumberScreenActions.GoBack -> {
                    onBackClicked.invoke()
                }
                CarNumberScreenActions.ErrorNumber -> {
                    Toast.makeText(activity, "Неправильний номер авто", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    var text by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Номер авто")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WhiteColor,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onBackClicked,
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = text,
                singleLine = true,
                placeholder = { Text(text = "KA 9900 HH") },
                onValueChange = { newText ->
                    text = newText
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    viewModel.saveNumber(text.text)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                ),
            ) {
                Text(text = "Зберегти")
            }
        }
    }
}