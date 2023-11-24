package com.latihanbyrg.tugaspertemuan12

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihanbyrg.tugaspertemuan12.adapter.CatAdapter
import com.latihanbyrg.tugaspertemuan12.database.model.CatTable
import com.latihanbyrg.tugaspertemuan12.databinding.FragmentExplorerBinding


class ExplorerFragment : Fragment() {
    private lateinit var catViewModel: CatViewModel
    private lateinit var catAdapter: CatAdapter


    private val binding by lazy {
        FragmentExplorerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = CatViewModelFactory((requireActivity().application as CatOPiasApplication).repository)
        catViewModel = ViewModelProvider(requireActivity(), factory)[CatViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Swipe Refresh Layout
        binding.swipeRefresh.setOnRefreshListener {
            catViewModel.clearExplorerData()
            catViewModel.fetchExplorerData(requireContext())
            binding.swipeRefresh.isRefreshing = false
        }

        // Recycler View
        catRecycler()


    }



    private fun catRecycler() {
        catViewModel.catExplorer.observe(viewLifecycleOwner){
            catAdapter = CatAdapter(
                cats = it,
                onClickBookmark = {catData ->


                    catViewModel.addCat(
                        CatTable(
                            catId = catData.id ?: "0",
                            url = catData.url ?: "https://avatars.githubusercontent.com/u/126530940?s=96&v=4",
                            weight = catData.breeds[0].weight.metric,
                            name = catData.breeds[0].name,
                            temperament = catData.breeds[0].temperament,
                            origin = catData.breeds[0].origin,
                            description = catData.breeds[0].description,
                            lifeSpan = catData.breeds[0].lifeSpan,
                        )
                    )
                    Log.i("catData", catData.toString())

                    Toast.makeText(
                        context,
                        "Cat has been added to bookmark",
                        Toast.LENGTH_SHORT
                    ).show()


                }
            )

            binding.rvCards.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = catAdapter
            }
        }
    }



}