package com.latihanbyrg.tugaspertemuan12

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.latihanbyrg.tugaspertemuan12.api.ApiService
import com.latihanbyrg.tugaspertemuan12.database.model.CatTable
import com.latihanbyrg.tugaspertemuan12.model.Cat
import com.latihanbyrg.tugaspertemuan12.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatViewModel(private val repository: Repository) : ViewModel(){
    val catExplorer : LiveData<ArrayList<Cat>> = repository.catExplorer

    var catList : LiveData<List<CatTable>> = repository.catsBookmarks.asLiveData()

    fun addCat(catTable: CatTable) = viewModelScope.launch {
        repository.insertCat(catTable)
    }

    fun updateCat(catTable: CatTable) = viewModelScope.launch {
        repository.updateCat(catTable)
    }

    fun unBookmarkCat(catTable: CatTable) = viewModelScope.launch {
        repository.deleteCat(catTable)
    }

    fun updatePetNameAndDescription(catId: String, petName: String, description: String) = viewModelScope.launch {
        repository.updatePetNameAndDescription(catId, petName, description)
    }

    fun fetchExplorerData(context: Context) {
        repository.BREEDS.forEach { breed ->
            repository.client.getCat(breed).enqueue(object : Callback<List<Cat>> {
                override fun onResponse(
                    call: Call<List<Cat>>,
                    response: Response<List<Cat>>
                ) {
                    val cats = response.body()
                    if (response.isSuccessful) {
                        if (cats != null) {
                           val tempListCats = repository.catExplorer.value ?: ArrayList()
                            tempListCats.addAll(cats)
                            repository.catExplorer.value = tempListCats
                        }
                    } else {
                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    call: Call<List<Cat>>,
                    t: Throwable
                ) {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun clearExplorerData() {
        repository.clearExplorerData()
    }


}

class CatViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("CatViewModelFactory", "create: ")
        if (modelClass.isAssignableFrom(CatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}