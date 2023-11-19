package com.latihanbyrg.tugaspertemuan12.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.latihanbyrg.tugaspertemuan12.api.ApiClient
import com.latihanbyrg.tugaspertemuan12.api.ApiService
import com.latihanbyrg.tugaspertemuan12.database.controller.CatTableDao
import com.latihanbyrg.tugaspertemuan12.database.model.CatTable
import com.latihanbyrg.tugaspertemuan12.model.Cat
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(
    private val catDao: CatTableDao,
) {

    // Getter
    private val _catsExplorer = MutableLiveData<ArrayList<Cat>>()
    val catExplorer : MutableLiveData<ArrayList<Cat>>
        get() = _catsExplorer

    val client: ApiService = ApiClient.getInstance()

    val catsBookmarks: Flow<List<CatTable>> = catDao.allCats

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()


    // Methods
    fun clearExplorerData() {
        _catsExplorer.value?.clear()
    }




    // Constant
    val BREEDS: List<String>
        get() = listOf(
            "beng",
            "amis",
            "bslo",
            "bamb",
            "bali",
            "birm",
            "awir",
            "abys",
            "acur",
        )



    // CRUD

    fun insertCat(catTable: CatTable) {
        executorService.execute {
            catDao.insert(catTable)
            Log.i("Repository", "insertCat: $catTable")
        }
    }


    fun updateCat(catTable: CatTable) {
        executorService.execute {
            catDao.update(catTable)
            Log.w("Repository", "updateCat: $catTable")
        }
    }

    fun updatePetNameAndDescription(catId: String, petName: String, description: String) {
        executorService.execute {
            catDao.updatePetNameAndDescription(catId, petName, description)
            Log.w("Repository", "updatePetNameAndDescription: $catId, $petName, $description")
        }
    }

    fun deleteCat(catTable: CatTable) {
        executorService.execute {
            catDao.delete(catTable)
            Log.d("Repository", "deleteCat: $catTable")
        }
    }


}
