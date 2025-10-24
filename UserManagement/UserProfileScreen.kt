package com.example.testassignment.UserManagement

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testassignment.R
import com.example.testassignment.ui.AppViewModelProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.retailease.ui.theme.Josefin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit,
    onUserEditClick: () -> Unit,
    onEnterClick: () -> Unit,
    onThemeChanged: (Boolean) -> Unit = {}, // Add this parameter to handle theme changes
    isDarkTheme: Boolean = isSystemInDarkTheme(), // Accept current theme state
    modifier: Modifier = Modifier,
    viewModel: AppEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val user by viewModel.user.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontFamily = Josefin
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Avatar Section
            Box(
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.generic_avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                // Edit icon overlay
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Avatar",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .background(
                            Color(0xFF4CAF50),
                            CircleShape
                        )
                        .padding(4.dp)
                        .clickable { onUserEditClick() }
                )
            }

            // Personal Details Section
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Personal Details",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Josefin
                )
                Text(
                    text = "Edit Profile",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = Josefin,
                    modifier = Modifier.clickable {
                        onUserEditClick()
                    }
                )
            }

            // User Details
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                UserDetailRow(
                    label = "Email",
                    value = user?.email ?: ""
                )

                UserDetailRow(
                    label = "Password",
                    value = "*".repeat(user?.password?.length ?: 8)
                )

                UserDetailRow(
                    label = "Nickname",
                    value = user?.nickname ?: ""
                )

                UserDetailRow(
                    label = "Company Name",
                    value = user?.companyName ?: ""
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Divider line above Settings
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Settings Section
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontFamily = Josefin,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Start
            )

            // Theme Setting
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = Josefin
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { newValue ->
                        onThemeChanged(newValue) // Pass the theme change up to parent
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                Button(
                    onClick = {
                        onEnterClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp), // Adjust the button height
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF27AE60), // Light green color
                        contentColor = Color.White // White text
                    )
                ) {
                    Text(
                        text = "Log Out",
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = Josefin
                    )
                }
                Button(
                    onClick = {
                        user?.let { viewModel.deleteUser(it) }
                        onEnterClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE74C3C), // Red color
                        contentColor = Color.White // White text
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        text = "Delete",
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = Josefin
                    )
                }
            }
        }
    }
}

@Composable
private fun UserDetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        // Left column - Labels
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = Josefin,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        // Right column - Values
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = Josefin,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
    }
}