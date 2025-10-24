package com.example.testassignment.UserManagement

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retailease.ui.theme.Josefin
import com.example.testassignment.R
import com.example.testassignment.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEditScreen(
    onNavigateBack: () -> Unit,
    onDeleteUser: () -> Unit = {},
    viewModel: AppEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val currentUser by viewModel.user.collectAsState()

    var email by remember(currentUser) { mutableStateOf(currentUser?.email.orEmpty()) }
    var password by remember(currentUser) { mutableStateOf(currentUser?.password.orEmpty()) }
    var nickname by remember(currentUser) { mutableStateOf(currentUser?.nickname.orEmpty()) }
    var companyName by remember(currentUser) { mutableStateOf(currentUser?.companyName.orEmpty()) }
    
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Show loading or error state if no user is loaded
    if (currentUser == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Loading user data...",
                fontSize = 18.sp,
                fontFamily = Josefin
            )
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.generic_avatar),
                contentDescription = "Avatar",
                modifier = Modifier.width(150.dp).height(150.dp)
            )
            Spacer(Modifier.padding(bottom = 30.dp))
            Text(
                text = "Email",
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Josefin,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 20.dp).fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Enter an email") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .width(375.dp)
            )
            Text(
                text = "Password",
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Josefin,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 20.dp).fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Enter a password") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .width(375.dp)
            )
            Text(
                text = "Nickname",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Josefin,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text(text = "Enter a nickname") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .width(375.dp)
            )
            Text(
                text = "Company Name",
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Josefin,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text(text = "Enter a company name") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .width(375.dp)
            )
            Button(
                onClick = {
                    // Validate input before updating
                    if (email.isNotBlank() && password.isNotBlank() && nickname.isNotBlank() && companyName.isNotBlank()) {
                        val user = currentUser?.copy(email = email, password = password, nickname = nickname, companyName = companyName)
                        if (user != null) {
                            viewModel.updateUserDetails(user)
                            onNavigateBack()
                        }
                    }
                },
                enabled = email.isNotBlank() && password.isNotBlank() && nickname.isNotBlank() && companyName.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD7E8CD), // Light green background
                    contentColor = Color(0xFF4A5043),    // Dark text color
                    disabledContainerColor = Color(0xFFE0E0E0), // Light gray when disabled
                    disabledContentColor = Color(0xFF9E9E9E)    // Gray text when disabled
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(10.dp).width(370.dp).height(50.dp)

            ) { Text(
                text = "Confirm",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Josefin
            ) }
            
            // Delete Account Button
            Button(
                onClick = { showDeleteDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE57373), // Light red background
                    contentColor = Color(0xFFD32F2F)    // Dark red text color
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(10.dp).width(370.dp).height(50.dp)
            ) { 
                Text(
                    text = "Delete Account",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = Josefin
                ) 
            }
        }
    }
    
    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { 
                Text(
                    "Delete Account",
                    fontFamily = Josefin,
                    fontWeight = FontWeight.Bold
                ) 
            },
            text = { 
                Text(
                    "Are you sure you want to delete your account? This action cannot be undone.",
                    fontFamily = Josefin
                ) 
            },
            confirmButton = {
                Button(
                    onClick = {
                        currentUser?.let { user ->
                            viewModel.deleteUser(user)
                            onDeleteUser()
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE57373)
                    )
                ) {
                    Text(
                        "Delete",
                        color = Color.White,
                        fontFamily = Josefin
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text(
                        "Cancel",
                        fontFamily = Josefin
                    )
                }
            }
        )
    }
}

