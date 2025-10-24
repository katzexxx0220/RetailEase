package com.example.testassignment.branchManagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.NavController
import com.example.retailease.ui.theme.Josefin
import com.example.testassignment.ProductManagement.ProductViewModel
import com.example.testassignment.data.Item
import com.example.testassignment.data.StockMovement

// Data class for aggregated item performance
data class ItemPerformance(
    val productCode: String,
    val productName: String,
    val totalStockIn: Int,
    val totalStockOut: Int,
    val initialQuantity: Int
)

@Composable
fun ItemPerformanceCard(
    item: ItemPerformance,
    isStockIn: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isStockIn) Color(0xFF81C784) else Color(0xFFE57373)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = item.productName,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Josefin,
                color = Color.White
            )
            
            Text(
                text = "Code: ${item.productCode}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Josefin,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsPerformance(
    modifier: Modifier = Modifier, 
    navController: NavController,
    productViewModel: ProductViewModel
) {
    val allItems by productViewModel.allItems.collectAsStateWithLifecycle(initialValue = emptyList())
    val stockMovements by productViewModel.stockFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    // Calculate top 3 stock in and stock out items
    val topStockInItems = calculateTopStockInItems(allItems, stockMovements).take(3)
    val topStockOutItems = calculateTopStockOutItems(allItems, stockMovements).take(3)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Items Performance",
                    fontFamily = Josefin,
                    fontWeight = FontWeight.Medium
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
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
            Spacer(modifier = Modifier.height(16.dp))

            // Top 3 Most Stock In Section
            Text(
                text = "Top 3 Most Stock In",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Josefin,
                color = Color(0xFF4A6741),
                modifier = Modifier.padding(horizontal = 22.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (topStockInItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No stock in data available",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Josefin,
                        color = Color(0xFF888888)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(topStockInItems) { item ->
                        ItemPerformanceCard(
                            item = item,
                            isStockIn = true
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Top 3 Most Stock Out Section
            Text(
                text = "Top 3 Most Stock Out",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Josefin,
                color = Color(0xFF4A6741),
                modifier = Modifier.padding(horizontal = 22.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (topStockOutItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No stock out data available",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Josefin,
                        color = Color(0xFF888888)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(topStockOutItems) { item ->
                        ItemPerformanceCard(
                            item = item,
                            isStockIn = false
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// Helper function to calculate top stock in items
private fun calculateTopStockInItems(items: List<Item>, stockMovements: List<StockMovement>): List<ItemPerformance> {

    val productResults = mutableListOf<ItemPerformance>()

    for (item in items) {
        val productCode = item.code
        val productName = item.name

        val productFlow = stockMovements.filter { movement -> movement.productCode == productCode }

        var totalStockIn = 0
        var totalStockOut = 0

        for (flow in productFlow) {
            if (flow.isStockIn) {
                totalStockIn += flow.quantity
            } else {
                totalStockOut += flow.quantity
            }
        }
        var initialQuantity = 0
        val stockInFlow = productFlow.filter { it.isStockIn }

        if (stockInFlow.isNotEmpty()) {
            val firstStockIn = stockInFlow.minByOrNull { it.timestampMillis }
            if (firstStockIn != null) {
                initialQuantity = firstStockIn.quantity
            }
        }
        val performance = ItemPerformance(
            productCode = productCode,
            productName = productName,
            totalStockIn = totalStockIn,
            totalStockOut = totalStockOut,
            initialQuantity = initialQuantity
        )
        if (totalStockIn > 0) {
            productResults.add(performance)
        }
    }

    return productResults.sortedByDescending { it.totalStockIn }
}

// Helper function to calculate top stock out items
private fun calculateTopStockOutItems(items: List<Item>, stockMovements: List<StockMovement>): List<ItemPerformance> {

    val productResults = mutableListOf<ItemPerformance>()

    for (item in items) {
        val productCode = item.code
        val productName = item.name

        val productFlow = stockMovements.filter { movement -> movement.productCode == productCode }

        var totalStockIn = 0
        var totalStockOut = 0

        for (flow in productFlow) {
            if (flow.isStockIn) {
                totalStockIn += flow.quantity
            } else {
                totalStockOut += flow.quantity
            }
        }
        var initialQuantity = 0
        val stockInFlow = productFlow.filter { it.isStockIn }

        if (stockInFlow.isNotEmpty()) {
            val firstStockIn = stockInFlow.minByOrNull { it.timestampMillis }
            if (firstStockIn != null) {
                initialQuantity = firstStockIn.quantity
            }
        }
        val performance = ItemPerformance(
            productCode = productCode,
            productName = productName,
            totalStockIn = totalStockIn,
            totalStockOut = totalStockOut,
            initialQuantity = initialQuantity
        )
        if (totalStockOut > 0) {
            productResults.add(performance)
        }
    }
    return productResults.sortedByDescending { it.totalStockOut }
}


