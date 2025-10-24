package com.example.testassignment.branchData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.example.testassignment.data.AppContainer
import com.example.testassignment.data.AppDataContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BranchViewModel(application: Application): AndroidViewModel(application) {

    val readAllBranch: Flow<List<Branch>>
    private val appRepository: com.example.testassignment.data.AppRepository

    init{
        val container: AppContainer = AppDataContainer(application)
        appRepository = container.appRepository
        readAllBranch = appRepository.getAllBranchesStream()
    }

    fun addBranch(branch: Branch){
        viewModelScope.launch(Dispatchers.IO){
            appRepository.insertBranch(branch)
        }
    }

    fun updateBranch(branch: Branch){
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.updateBranch(branch)
        }
    }

    fun deleteBranch(branch: Branch){
        viewModelScope.launch(Dispatchers.IO){
            appRepository.deleteBranchWithCascade(branch)
        }
    }
}
