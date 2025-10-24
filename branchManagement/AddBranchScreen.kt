package com.example.testassignment.branchManagement
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.retailease.ui.theme.Josefin
import com.example.testassignment.branchData.Branch
import com.example.testassignment.branchData.BranchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBranchScreen(
    modifier: Modifier = Modifier, 
    navController: NavHostController,
    onNavigateBack: () -> Unit = { navController.navigate("home_screen") }
) {

    val branchViewModel: BranchViewModel = viewModel()

    var branchName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }

    // Dialog states
    var showSaveConfirm by remember { mutableStateOf(false) }
    var showSaveSuccess by remember { mutableStateOf(false) }
    var showDiscardConfirm by remember { mutableStateOf(false) }
    
    // Validation state
    var showValidationError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { 
                Text(
                    text = "Add Branch",
                    fontFamily = Josefin,
                    fontWeight = FontWeight.Medium
                ) 
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
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

    // main content start here
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F0F0))
                .padding(12.dp)
        ) {

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA8C8A1)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Add Branch",
                color = Color(0xFF2D4A26),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Josefin
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Branch Name",
            color = Color(0xFF6B6B6B),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Josefin,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = branchName,
            onValueChange = {
                branchName = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD0D0D0),
                focusedBorderColor = Color(0xFF4A6741),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Address Section
        Text(
            text = "Address",
            color = Color(0xFF6B6B6B),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Josefin,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD0D0D0),
                focusedBorderColor = Color(0xFF4A6741),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = false
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Contact Section
        Text(
            text = "Contact",
            color = Color(0xFF6B6B6B),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Josefin,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = contact,
            onValueChange = { contact = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD0D0D0),
                focusedBorderColor = Color(0xFF4A6741),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Save Button
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if(branchName.isNotBlank() && address.isNotBlank() && contact.isNotBlank()){
                        showValidationError = false
                        showSaveConfirm = true
                    } else {
                        showValidationError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.Center)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA8C8A1)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Save",
                    color = Color(0xFF2D4A26),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Josefin
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    showDiscardConfirm = true
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.Center)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE8A5A5)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Discard",
                    color = Color(0xFF8B3A3A),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Josefin
                )
            }
        }

        // Validation Error Message
        if (showValidationError) {
            Text(
                text = "No Empty Field",
                color = Color.Red,
                fontSize = 18.sp,
                fontFamily = Josefin,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }

    // Confirm Save dialog
    if (showSaveConfirm) {
        AlertDialog(
            onDismissRequest = { showSaveConfirm = false },
            title = { Text("All done? Hit yes!", fontFamily = Josefin) },
            confirmButton = {
                Button(
                    onClick = {
                        val branch = Branch(
                            branchName = branchName.trim(),
                            address = address.trim(),
                            contact = contact.trim()
                        )
                        branchViewModel.addBranch(branch)
                        showSaveConfirm = false
                        showSaveSuccess = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA8C8A1))
                ) { Text(
                    text = "Yes",
                    color = Color(0xFF2D4A26),
                    fontFamily = Josefin)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showSaveConfirm = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8A5A5))
                ) { Text(
                    text = "No",
                    color = Color(0xFF8B3A3A),
                    fontFamily = Josefin)
                }
            }
        )
    }

    // Save success dialog
    if (showSaveSuccess) {
        AlertDialog(
            onDismissRequest = { showSaveSuccess = false },
            title = { Text(
                text ="Update Successfully!",
                fontFamily = Josefin)
                    },
            text = {},
            confirmButton = {
                Button(
                    onClick = {
                        showSaveSuccess = false
                        onNavigateBack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA8C8A1))
                ) { Text(
                    text = "OK",
                    color = Color(0xFF2D4A26),
                    fontFamily = Josefin)
                }
            }
        )
    }

    // Discard confirm dialog
    if (showDiscardConfirm) {
        AlertDialog(
            onDismissRequest = { showDiscardConfirm = false },
            title = { Text("Cancel Add?", fontFamily = Josefin) },
            text = {},
            confirmButton = {
                Button(
                    onClick = {
                        branchName = ""
                        address = ""
                        contact = ""
                        showDiscardConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA8C8A1))
                ) { Text(
                    text = "Yes",
                    color = Color(0xFF2D4A26),
                    fontFamily = Josefin)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDiscardConfirm = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8A5A5))
                ) { Text(
                    text = "No",
                    color = Color(0xFF8B3A3A),
                    fontFamily = Josefin)
                }
            }
        )
        }
    }
}
