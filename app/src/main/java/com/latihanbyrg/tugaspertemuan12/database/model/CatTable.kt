package com.latihanbyrg.tugaspertemuan12.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_table")
data class CatTable(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,

    @ColumnInfo(name = "cat_id")
    var catId: String? = id.toString(),

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "weight")
    var weight: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "temperament")
    var temperament: String,

    @ColumnInfo(name = "origin")
    var origin: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "life_span")
    var lifeSpan: String,

    @ColumnInfo(name = "pet_name")
    var petName: String? = "Favourite Cat $id",


)
