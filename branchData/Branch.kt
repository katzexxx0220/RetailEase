package com.example.testassignment.branchData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "branches")
data class Branch(
    @PrimaryKey
    val branchName: String,
    val address: String,
    val contact: String
)

