package com.example.testassignment.UserManagement

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retailease.ui.theme.Josefin
import com.example.testassignment.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    viewModel: AppEntryViewModel,
    onNavigateHome: () -> Unit,
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Sign Up") },
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
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier.padding(vertical = 70.dp))
            Image(
                painter = painterResource(R.drawable.generic_avatar),
                contentDescription = "Avatar",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier.padding(bottom = 50.dp))
            Text(
                text = "Email",
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Josefin,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = modifier.padding(start = 20.dp).fillMaxWidth()
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
                modifier = modifier
                    .padding(bottom = 25.dp)
                    .fillMaxWidth(0.9f)
            )
            Text(
                text = "Password",
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Josefin,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = modifier.padding(start = 20.dp).fillMaxWidth()
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
                modifier = modifier
                    .padding(bottom = 25.dp)
                    .fillMaxWidth(0.9f)
            )
            Text(
                text = "Nickname",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Josefin,
                textAlign = TextAlign.Left,
                modifier = modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text(text = "Enter a nickname") },
                shape = RoundedCornerShape(16.dp),
                modifier = modifier
                    .padding(bottom = 25.dp)
                    .fillMaxWidth(0.9f)
            )
            Text(
                text = "Company Name",
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Josefin,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text(text = "Enter a company name") },
                shape = RoundedCornerShape(16.dp),
                modifier = modifier
                    .padding(bottom = 15.dp)
                    .fillMaxWidth(0.9f)
            )
            Button(
                onClick = {
                    viewModel.register(email, password, nickname, companyName)
                    onNavigateHome()
                }, // Register the user into database and navigate to home screen
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD7E8CD), // Light green background
                    contentColor = Color(0xFF4A5043)    // Dark text color
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(10.dp).fillMaxWidth(0.7f).height(50.dp)
            ) {
                Text(
                    text = "Sign Up",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = Josefin
                )
            }
        }
    }
}