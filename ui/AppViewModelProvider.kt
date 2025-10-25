package com.example.testassignment.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.testassignment.UserManagement.AppEntryViewModel
import com.example.testassignment.ProductManagement.ProductViewModel
import com.example.testassignment.branchData.BranchViewModel
import com.example.testassignment.data.inventory.RetailApplication

/**
 * Provides Factory to create instance of ViewModel for the entire Retail app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for AppEntryViewModel
        initializer {
            AppEntryViewModel(retailApplication().container.appRepository)
        }
        // Initializer for ProductViewModel
        initializer {
            ProductViewModel(retailApplication())
        }
        // Initializer for BranchViewModel
        initializer {
            BranchViewModel(retailApplication())
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [RetailApplication].
 */
fun CreationExtras.retailApplication(): RetailApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as RetailApplication)

