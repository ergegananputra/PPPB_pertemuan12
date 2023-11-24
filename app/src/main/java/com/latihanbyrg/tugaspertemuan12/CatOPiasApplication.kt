package com.latihanbyrg.tugaspertemuan12

import android.app.Application
import com.latihanbyrg.tugaspertemuan12.repository.Repository


class CatOPiasApplication: Application() {
    val repository by lazy {
        Repository()
    }


}