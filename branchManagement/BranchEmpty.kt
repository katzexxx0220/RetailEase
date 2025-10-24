package com.example.testassignment.branchManagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.retailease.ui.theme.Josefin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BranchEmpty(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { 
                Text(
                    text = "Branch(es) List",
                    fontFamily = Josefin,
                    fontWeight = FontWeight.Medium
                ) 
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate("home_screen") }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back to Home"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF4A6741),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F0F0))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = "Nothing here yet",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Josefin,
                color = Color(0xFF424242),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))

        }
    }
}
