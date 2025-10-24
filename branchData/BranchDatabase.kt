package com.example.testassignment.branchData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Branch::class], version = 1, exportSchema = false)
abstract class BranchDatabase: RoomDatabase() {

    abstract fun branchDao(): BranchDao

    companion object{
        @Volatile
        private var Instance: BranchDatabase? = null

        fun getDatabase(context: Context): BranchDatabase{
            val tempInstance = Instance
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BranchDatabase::class.java,
                    "branches"
                ).fallbackToDestructiveMigration().build()

                Instance = instance
                return instance
            }
        }
    }
}

