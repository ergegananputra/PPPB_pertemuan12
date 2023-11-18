package com.latihanbyrg.tugaspertemuan12

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.latihanbyrg.tugaspertemuan12.adapter.CatAdapter
import com.latihanbyrg.tugaspertemuan12.database.model.CatTable
import com.latihanbyrg.tugaspertemuan12.databinding.FragmentExplorerBinding
import com.latihanbyrg.tugaspertemuan12.model.Cat



class ExplorerFragment : Fragment() {
    private lateinit var catViewModel: CatViewModel


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
        catRecycler(catViewModel.catExplorer.value ?: ArrayList())


        catViewModel.catExplorer.observe(viewLifecycleOwner, Observer { list ->
            val adapter = CatAdapter(
                cats = list,
                onClickBookmark = {catData ->


                    catViewModel.addCat(
                        CatTable(
                            catId = catData.id,
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

                    Snackbar.make(
                        binding.root,
                        "Cat has been added to bookmark",
                        Snackbar.LENGTH_SHORT
                    ).show()


                }
            )
            binding.rvCards.adapter = adapter
            adapter.notifyDataSetChanged()
        })




    }



    private fun catRecycler(listCats: ArrayList<Cat>) {
        with(binding) {
            rvCards.apply {
                adapter = CatAdapter(
                    cats = listCats,
                    onClickBookmark = {catData ->

                        catViewModel.addCat(
                            CatTable(
                                catId = catData.id,
                                url = catData.url ?: "https://avatars.githubusercontent.com/u/126530940?s=96&v=4",
                                weight = catData.breeds[0].weight.metric,
                                name = catData.breeds[0].name,
                                temperament = catData.breeds[0].temperament,
                                origin = catData.breeds[0].origin,
                                description = catData.breeds[0].description,
                                lifeSpan = catData.breeds[0].lifeSpan,
                            )
                        )
                        adapter?.notifyItemInserted(listCats.size)
                        Log.i("catData", catData.toString())

                        Snackbar.make(
                            binding.root,
                            "Cat has been added to bookmark",
                            Snackbar.LENGTH_SHORT
                        ).show()


                    }
                )
                layoutManager = LinearLayoutManager(context)
            }
        }
    }



}