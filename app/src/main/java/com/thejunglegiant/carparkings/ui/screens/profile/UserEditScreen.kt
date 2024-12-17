package com.thejunglegiant.carparkings.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun UserEditScreen(
    name: String,
    surname: String,
    email: String,
    gender: String,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onSave: () -> Unit
) {
    var selectedGender by remember { mutableStateOf(gender) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Імʼя") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = surname,
            onValueChange = onSurnameChange,
            label = { Text("Прізвище") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Стать")

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedGender == "Чоловік",
                onClick = {
                    selectedGender = "Чоловік"
                    onGenderChange("Чоловік")
                }
            )
            Text("Чоловік")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = selectedGender == "Жінка",
                onClick = {
                    selectedGender = "Жінка"
                    onGenderChange("Жінка")
                }
            )
            Text("Жінка")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSave,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}
