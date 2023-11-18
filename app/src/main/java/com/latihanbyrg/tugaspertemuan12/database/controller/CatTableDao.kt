package com.latihanbyrg.tugaspertemuan12.database.controller

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.latihanbyrg.tugaspertemuan12.database.model.CatTable
import kotlinx.coroutines.flow.Flow

@Dao
interface CatTableDao {

    @get:Query("SELECT * FROM cat_table ORDER BY id ASC")
    val allCats: Flow<List<CatTable>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(catTable: CatTable)
    
    @Update
    fun update(catTable: CatTable)

    @Delete
    fun delete(catTable: CatTable)


    @Query("UPDATE cat_table SET pet_name = :petName, description = :description WHERE cat_id = :catId")
    fun updatePetNameAndDescription(catId: String, petName: String, description: String)

}