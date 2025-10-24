package com.example.testassignment.branchData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BranchDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBranch(branch: Branch)

    @Query("SELECT * FROM branches ORDER BY branchName ASC")
    fun readAllBranch(): Flow<List<Branch>>

    @Update
    suspend fun updateBranch(branch: Branch)

    @Delete
    suspend fun deleteBranch(branch: Branch)

}
