package com.latihanbyrg.tugaspertemuan12.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.latihanbyrg.tugaspertemuan12.api.ApiClient
import com.latihanbyrg.tugaspertemuan12.api.ApiService
import com.latihanbyrg.tugaspertemuan12.database.model.CatTable
import com.latihanbyrg.tugaspertemuan12.model.Cat

class Repository {

    private val firebase = Firebase.firestore
    val catCollectionRef = firebase.collection("catsCollections")

    // Getter
    private val _catsExplorer = MutableLiveData<ArrayList<Cat>>()
    val catExplorer : MutableLiveData<ArrayList<Cat>>
        get() = _catsExplorer

    val client: ApiService = ApiClient.getInstance()

    val catsBookmarks: MutableLiveData<List<CatTable>> by lazy {
        MutableLiveData<List<CatTable>>()
    }


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
        catCollectionRef.add(catTable).addOnSuccessListener { documentReference ->
            Log.d("Repository", "DocumentSnapshot written with ID: ${documentReference.id}")
            catTable.id = documentReference.id
            documentReference.set(catTable).addOnFailureListener {
                Log.w("Repository", "Error adding document", it)
            }
        }.addOnFailureListener { e ->
            Log.w("Repository", "Error adding document", e)
        }
    }


    fun updateCat(catTable: CatTable) {
        Log.d("Repository", "updateCat: $catTable")
        catCollectionRef.document(catTable.id).set(catTable).addOnFailureListener {
            Log.e("Repository", "Error updating cat", it)
        }
    }

    fun updatePetNameAndDescription(catId: String, petName: String, description: String) {
        Log.d("Repository", "updatePetNameAndDescription: $catId, $petName, $description")
        catCollectionRef.document(catId).update("petName", petName, "description", description)
            .addOnFailureListener {
                Log.e("Repository", "Error updating petName and description", it)
            }

    }

    fun deleteCat(catTable: CatTable) {
        if (catTable.id.isEmpty()) {
            Log.e("Repository", "deleteCat: missing cat id")
            return
        }
        catCollectionRef.document(catTable.id).delete().addOnFailureListener {
            Log.e("Repository", "Error deleting cat", it)
        }
    }


}
