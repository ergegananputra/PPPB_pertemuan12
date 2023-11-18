package com.latihanbyrg.tugaspertemuan12

import android.app.Application
import com.latihanbyrg.tugaspertemuan12.database.CatDatabase
import com.latihanbyrg.tugaspertemuan12.repository.Repository


class CatOPiasApplication: Application() {
    private val database by lazy {
        CatDatabase.getDatabase(this)
    }
    val repository by lazy {
        Repository(database.catDao())
    }


}