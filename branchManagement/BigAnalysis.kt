package com.example.testassignment.branchManagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults    
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import com.example.testassignment.R
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retailease.ui.theme.Josefin
import com.example.testassignment.branchData.BranchViewModel
import com.example.testassignment.ProductManagement.ProductViewModel
import com.example.testassignment.data.StockMovement

private fun calculateTotalCurrentStock(stockMovements: List<StockMovement>): Int {

    // group all stock flow by product code first
    val stockFlow = stockMovements.groupBy { it.productCode }
    var totalStock = 0

    // go through them
    for((productCode, flows) in stockFlow){
        var currentStock = 0

        for(flow in flows){
            if(flow.isStockIn){
                currentStock += flow.quantity
            }
            else{
                currentStock -= flow.quantity
            }
        }
        if(currentStock < 0){
            currentStock = 0
        }
        totalStock += currentStock
    }
    return totalStock
}

@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit,
    onProductListClick: () -> Unit,
    onAnalysisClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFFF5F0F0),
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        NavigationBarItem(
            selected = false,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color(0xFF4A6741)) },
            label = { Text("Home", fontSize = 12.sp, color = Color(0xFF4A6741)) }
        )
        NavigationBarItem(
            selected = false,
            onClick = onProductListClick,
            icon = { Icon(Icons.Default.List, contentDescription = "Product List", tint = Color(0xFF4A6741)) },
            label = { Text("Product List", fontSize = 12.sp, color = Color(0xFF4A6741)) }
        )
        NavigationBarItem(
            selected = true,
            onClick = onAnalysisClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_bar_chart_24),
                    contentDescription = "Analysis",
                    tint = Color(0xFF4A6741)
                )
            },
            label = { Text("Analysis", fontSize = 12.sp, color = Color(0xFF4A6741)) }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BigAnalysis(
    modifier: Modifier = Modifier,
    branchViewModel: BranchViewModel,
    productViewModel: ProductViewModel,
    onHomeClick: () -> Unit = {},
    onProductListClick: () -> Unit = {},
    onAnalysisClick: () -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    onAnalysisBranchClick: () -> Unit = {},
) {
    // Observe real data from ViewModels
    val branches by branchViewModel.readAllBranch.collectAsStateWithLifecycle(initialValue = emptyList())
    val stockMovements by productViewModel.stockFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    val totalBranches = branches.size

    // Calculate total current stock across all products
    val totalStock = calculateTotalCurrentStock(stockMovements)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0F0))
    ) {
        // Top App Bar
        TopAppBar(
            title = { null },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF4A6741),
                titleContentColor = Color.White
            )
        )

        // Main content with proper spacing
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Analysis Button
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A6741)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Analysis",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Josefin
                )
            }

            // Total Branch(es) Button
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Branch(es)",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = Josefin
                    )
                    Text(
                        text = totalBranches.toString(),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Josefin
                    )
                }
            }

            // Cards Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFB74D)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
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
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Total Stock",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = Josefin
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF90CAF9)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = totalBranches.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = Josefin
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Total Branches",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = Josefin
                        )
                    }
                }
            }

            // Top 3 Most Stocked In & Out Button
            Button(
                onClick = { onSeeAllClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC5CAE9)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Top 3 Most Stocked In & Out",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Josefin
                )
            }

            // Branch Analysis Button
            Button(
                onClick = { onAnalysisBranchClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF9A9A)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Branch Analysis",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Josefin
                )
            }

        }
        // Bottom Navigation Bar
        BottomNavigationBar(
            onHomeClick = onHomeClick,
            onProductListClick = onProductListClick,
            onAnalysisClick = onAnalysisClick
        )
    }
}

