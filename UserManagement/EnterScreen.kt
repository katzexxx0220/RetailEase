package com.example.testassignment.UserManagement

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retailease.ui.theme.Josefin
import com.example.testassignment.R

@Preview
@Composable
fun EnterScreen(
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    modifier: Modifier = Modifier){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.generic_avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier.padding(10.dp))
        Button(
            onClick = onLoginClick, //navigate to Login Screen
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD7E8CD), // Light green background
                contentColor = Color(0xFF4A5043)    // Dark text color
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = modifier.padding(10.dp).width(250.dp).height(50.dp)
        ){
            Text(
                text = "Log In",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Josefin
            )
        }
        Text(
            text = "OR",
            color = Color.Gray,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = Josefin,
            modifier = modifier.padding(10.dp).fillMaxWidth()
        )
        Button(
            onClick = onSignUpClick, //navigate to SignUp Screen
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF758A62), // Dark green background
                contentColor = Color.White          // White text
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = modifier.padding(10.dp).width(250.dp).height(50.dp)
        ){
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