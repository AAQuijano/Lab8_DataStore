package com.quijano.lab_7_avatarbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DataStoreScreen(viewModel: DataStoreViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .background(Color.DarkGray)
            .padding(16.dp)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        item {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ){
                Image(
                    painter = painterResource(id = R.drawable.android_bot), // Asegúrate de tener esta imagen en tus recursos
                    contentDescription = "Android Bot",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

        }

        item{
            TextField(
                value = uiState.name,
                onValueChange = {
                    //stringInput = it
                    viewModel.updateName(it)
                },
                label = { Text("Name") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Red
                )
            )
        }

        item{
            TextField(
                value = uiState.role,
                onValueChange = {
                    //roleInput = it
                    viewModel.updateRole(it)
                },
                label = { Text("Role") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Red
                )
            )
        }
        item{
            Column {
                Text(
                    text = "Experience",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 55.dp, end = 15.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(start = 55.dp, end = 30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${uiState.year}")
                    Slider(
                        value = uiState.year.toFloat(),
                        onValueChange = {
                            viewModel.updateYear(it.toInt())
                        },
                        valueRange = 0f..50f,
                    )
                }
            }
        }

        item {
            TextField(
                value = uiState.phone,
                onValueChange = {
                    viewModel.updatePhone(it)
                },
                label = { Text("Phone") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Red
                )
            )
        }

        item {
            TextField(
                value = uiState.handle,
                onValueChange = {
                    viewModel.updateHandle(it)
                },
                label = { Text("Handle") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Red
                )
            )
        }

        item {
            TextField(
                value = uiState.email,
                onValueChange = {
                    viewModel.updateEmail(it)
                },
                label = { Text("Email") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Red
                )
            )
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Show Contact Info")
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = uiState.showContactInfo,
                    onCheckedChange = {
                        viewModel.toggleContactInfo()
                    }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Button(onClick = {
                viewModel.saveValuesInDataStore()
                viewModel.toggleSettings()
            }) {
                Text("Save & Close")
            }
        }
    }


}