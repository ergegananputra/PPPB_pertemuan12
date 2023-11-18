package com.latihanbyrg.tugaspertemuan12.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.latihanbyrg.tugaspertemuan12.database.controller.CatTableDao
import com.latihanbyrg.tugaspertemuan12.database.model.CatTable

@Database(entities = [CatTable::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase(){
    abstract fun catDao(): CatTableDao

    companion object{
        @Volatile
        private var INSTANCE: CatDatabase? = null

        fun getDatabase(context: Context): CatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    CatDatabase::class.java,
                    "cat_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }


    }



}