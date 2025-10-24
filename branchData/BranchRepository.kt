package com.example.testassignment.branchData

import kotlinx.coroutines.flow.Flow

class BranchRepository(private val branchDao: BranchDao) {

    val readAllBranch: Flow<List<Branch>> = branchDao.readAllBranch()

    suspend fun addBranch(branch: Branch){
        branchDao.addBranch(branch)
    }

    suspend fun updateBranch(branch: Branch){
        branchDao.updateBranch(branch)
    }

    suspend fun deleteBranch(branch: Branch){
        branchDao.deleteBranch(branch)
    }
}
