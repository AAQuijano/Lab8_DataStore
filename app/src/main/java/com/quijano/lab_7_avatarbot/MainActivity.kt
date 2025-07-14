package com.quijano.lab_7_avatarbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.quijano.lab_7_avatarbot.ui.theme.Lab_7_AvatarBotTheme

class MainActivity : ComponentActivity() {
    private val myViewModel by viewModels<DataStoreViewModel> {
        DataStoreViewModelFactory(this.application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab_7_AvatarBotTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    myViewModel.toggleSettings()
                                }
                            )
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by myViewModel.uiState.collectAsState()

                    if (uiState.showSettings) {
                        DataStoreScreen(viewModel = myViewModel)
                    } else {
                        BusinessCard(
                            profileInfo = ProfileInfo(
                                name = uiState.name,
                                role = uiState.role,
                                year = uiState.year
                            ),
                            contactInfo = ContactInfo(
                                phone = uiState.phone,
                                handle = uiState.handle,
                                email = uiState.email,
                                showContactInfo = uiState.showContactInfo
                            ),
                            showContactInfo = uiState.showContactInfo
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessCard(
    profileInfo: ProfileInfo,
    contactInfo: ContactInfo,
    showContactInfo: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        ProfileInfo(
            profileInfo = profileInfo,
            modifier = modifier.align(Alignment.Center)
        )
        ContactInfo(
            contactInfo = contactInfo,
            showContactInfo = showContactInfo,
            modifier = modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ProfileInfo(profileInfo: ProfileInfo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.White)
        ){
            Image(
                painter = painterResource(id = R.drawable.android_bot),
                contentDescription = "Android Bot",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = profileInfo.name,
            fontSize = 40.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
        )

        Text(
            text = profileInfo.role,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF006D3A),
        )

        Text(
            text = "${profileInfo.year} years of Experience",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ContactInfo(
    contactInfo: ContactInfo,
    showContactInfo: Boolean,
    modifier: Modifier
) {
    if (showContactInfo) {
        Column(modifier = modifier.padding(bottom = 20.dp)) {
            ContactRow(text = contactInfo.phone, icon = Icons.Filled.Call)
            ContactRow(text = contactInfo.handle, icon = Icons.Filled.Share)
            ContactRow(text = contactInfo.email, icon = Icons.Filled.Email)
        }
    }
}

@Composable
fun ContactRow(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}