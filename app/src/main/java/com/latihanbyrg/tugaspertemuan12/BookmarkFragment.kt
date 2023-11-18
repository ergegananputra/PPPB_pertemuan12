package com.latihanbyrg.tugaspertemuan12

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihanbyrg.tugaspertemuan12.EditActivity.Companion.EXTRA_CAT_ID
import com.latihanbyrg.tugaspertemuan12.EditActivity.Companion.EXTRA_DESCRIPTION
import com.latihanbyrg.tugaspertemuan12.EditActivity.Companion.EXTRA_IMAGE_URL
import com.latihanbyrg.tugaspertemuan12.EditActivity.Companion.EXTRA_PET_NAME
import com.latihanbyrg.tugaspertemuan12.adapter.CatAdapter
import com.latihanbyrg.tugaspertemuan12.adapter.CatTableAdapter
import com.latihanbyrg.tugaspertemuan12.database.model.CatTable
import com.latihanbyrg.tugaspertemuan12.databinding.FragmentBookmarkBinding
import com.latihanbyrg.tugaspertemuan12.model.Cat

class BookmarkFragment : Fragment() {
    private lateinit var catViewModel: CatViewModel
    private lateinit var catTableAdapter: CatTableAdapter
    private lateinit var launcher: ActivityResultLauncher<Intent>


    private val binding by lazy {
        FragmentBookmarkBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = CatViewModelFactory((requireActivity().application as CatOPiasApplication).repository)
        catViewModel = ViewModelProvider(requireActivity(), factory)[CatViewModel::class.java]


        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val catId = result.data?.getStringExtra(EXTRA_CAT_ID) ?: "0"
                val petName = result.data?.getStringExtra(EXTRA_PET_NAME) ?: "0"
                val description = result.data?.getStringExtra(EXTRA_DESCRIPTION) ?: "0"

                catViewModel.updatePetNameAndDescription(
                    catId,
                    petName,
                    description
                )

                Log.d("BookmarkFragment", "catId: $catId")
                catTableAdapter.notifyDataSetChanged()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Recycler View
        catRecycler()

    }

    private fun catRecycler() {
        catViewModel.catList.observe(viewLifecycleOwner){
            // Adapter
            catTableAdapter = CatTableAdapter(
                cats = catViewModel.catList.value ?: emptyList(),
                onLongPressCard = { catData ->
                    // intent to Edit Activity
                    val intentToEditActivity = Intent(activity, EditActivity::class.java)
                        .apply {
                            putExtra(EXTRA_CAT_ID, catData.catId)
                            putExtra(EXTRA_PET_NAME, catData.petName)
                            putExtra(EXTRA_IMAGE_URL, catData.url)
                            putExtra(EXTRA_DESCRIPTION, catData.description)
                        }
                    launcher.launch(intentToEditActivity)
                },
                onClickBookmark = { catData ->
                    val position = catViewModel.catList.value?.indexOf(catData) ?: -1
                    if (position != -1) {
                        catViewModel.unBookmarkCat(catData)
                    }
                }
            )



            binding.rvCards.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = catTableAdapter
            }
        }
    }



}