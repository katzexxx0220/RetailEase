package com.example.testassignment.branchManagement
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.retailease.ui.theme.Josefin
import com.example.testassignment.branchData.Branch
import com.example.testassignment.branchData.BranchViewModel

@Composable
fun BranchCard(branch: Branch, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = branch.branchName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Josefin,
                    color = Color(0xFF4A6741)
                )

                Text(
                    text = branch.address,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Josefin,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = branch.contact,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Josefin,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            TextButton(
                onClick = onDeleteClick
            ) {
                Text(
                    text = "Delete",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Josefin,
                    color = Color(0xFFE57373)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BranchList(
    modifier: Modifier = Modifier, 
    navController: NavHostController,
    branchViewModel: BranchViewModel
) {
    val branches by branchViewModel.readAllBranch.collectAsStateWithLifecycle(initialValue = emptyList())
    var showConfirm by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var branchToDelete by remember { mutableStateOf<Branch?>(null) }


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Branch List",
                    fontFamily = Josefin,
                    fontWeight = FontWeight.Medium
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate("home_screen") }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
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
        ) {

            // Branch List
            if (branches.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No branches found",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Josefin,
                        color = Color(0xFF888888)
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(26.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 22.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    items(branches) { branch ->
                        BranchCard(
                            branch = branch,
                            onDeleteClick = {
                                branchToDelete = branch
                                showConfirm = true
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            if (showConfirm) {
                AlertDialog(
                    onDismissRequest = { showConfirm = false },
                    title = { Text(text = "Confirm delete?", fontFamily = Josefin) },
                    text = { Text(text = "This cannot be undone.", fontFamily = Josefin) },
                    confirmButton = {
                        Button(
                            onClick = {
                                val target = branchToDelete
                                if (target != null) {
                                    branchViewModel.deleteBranch(target)
                                }
                                showConfirm = false
                                showSuccess = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA8C8A1))
                        ) { Text("Yes", color = Color(0xFF2D4A26), fontFamily = Josefin) }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showConfirm = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8A5A5))
                        ) { Text("No", color = Color(0xFF8B3A3A), fontFamily = Josefin) }
                    }
                )
            }

            if (showSuccess) {
                AlertDialog(
                    onDismissRequest = { showSuccess = false },
                    title = { Text(text = "Delete Successfully!", fontFamily = Josefin) },
                    text = {},
                    confirmButton = {
                        Button(
                            onClick = { showSuccess = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA8C8A1))
                        ) { Text("OK", color = Color(0xFF2D4A26), fontFamily = Josefin) }
                    }
                )
            }
        }
    }
}





