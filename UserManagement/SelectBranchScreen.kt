package com.example.testassignment.UserManagement

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testassignment.components.BranchDropdown

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectBranchScreen(
    branchViewModel: com.example.testassignment.branchData.BranchViewModel,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onClick: () -> Unit = {},
    onBranchSelected: (String) -> Unit
){
    var confirmed by remember { mutableStateOf(false) }
    var selectedBranchCode by remember { mutableStateOf<String?>(null) }
    
    // Get branches from ViewModel
    val branches by branchViewModel.readAllBranch.collectAsStateWithLifecycle(initialValue = emptyList())
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Select Branch") },
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
            verticalArrangement = Arrangement.Top,
            modifier = modifier.fillMaxSize()
        ){
            Spacer(Modifier.height(72.dp))
            }
            if (!confirmed) {
                Box(modifier = modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(0.8f)
                            .padding(horizontal = 16.dp)
                    ) {
                        BranchDropdown(
                            selectedCode = selectedBranchCode,
                            onSelected = { code ->
                                selectedBranchCode = code
                                onBranchSelected(code)
                            },
                            branches = branches,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = { 
                                confirmed = true
                                onClick() // Trigger navigation
                            },
                            enabled = selectedBranchCode != null,
                            modifier = Modifier.fillMaxWidth(0.5f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF5C9D6E),
                                contentColor = Color.White
                            )
                        ) { Text("Confirm") }
                    }
                }
            }
        }
    }
