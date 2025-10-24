package com.example.testassignment.UserManagement

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retailease.ui.theme.Josefin
import com.example.testassignment.ProductManagement.ProductViewModel
import com.example.testassignment.branchData.BranchViewModel
import com.example.testassignment.data.Item
import com.example.testassignment.data.StockMovement
import com.example.testassignment.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisBranch(
    branchCode: String?,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {}
) {
    // Get ViewModels
    val productViewModel: ProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val branchViewModel: BranchViewModel = viewModel(factory = AppViewModelProvider.Factory)
    
    // Get data from ViewModels
    val allItems by productViewModel.allItems.collectAsStateWithLifecycle(initialValue = emptyList())
    val stockMovements by productViewModel.stockFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val branches by branchViewModel.readAllBranch.collectAsStateWithLifecycle(initialValue = emptyList())
    
    // Find the selected branch
    val selectedBranch = branches.find { it.branchName == branchCode }
    
    // Filter data for the selected branch
    val branchItems = allItems.filter { it.branch == branchCode }
    val branchStockMovements = stockMovements.filter { it.branchCode == branchCode }
    
    // Calculate totals for this branch
    val totalProducts = branchItems.size
    val totalStock = calculateBranchTotalStock(branchStockMovements)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0F0))
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Branch Analysis",
                    fontFamily = Josefin,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
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

        // Main content with proper spacing
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Branch Name Button
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50).copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = selectedBranch?.branchName ?: "No branch selected",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Josefin
                )
            }

            // Cards Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFB74D)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = totalProducts.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = Josefin
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(
                            text = "Total Products",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = Josefin
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF90CAF9)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = totalStock.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = Josefin
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(
                            text = "Total Stock",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = Josefin
                        )
                    }
                }
            }

        }
    }
}

// Helper function to calculate total stock for a branch
private fun calculateBranchTotalStock(stockMovements: List<StockMovement>): Int {
    return stockMovements
        .groupBy { it.productCode }
        .map { (_, movements) ->
            // Calculate current stock for this product in this branch
            movements.fold(0) { currentStock, movement ->
                when {
                    movement.isStockIn -> currentStock + movement.quantity
                    else -> currentStock - movement.quantity
                }
            }.coerceAtLeast(0) // Ensure stock never goes below 0
        }
        .sum()
}


