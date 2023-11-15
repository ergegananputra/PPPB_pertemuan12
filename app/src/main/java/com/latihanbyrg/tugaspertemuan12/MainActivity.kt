package com.latihanbyrg.tugaspertemuan12

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihanbyrg.tugaspertemuan12.adapter.CatAdapter
import com.latihanbyrg.tugaspertemuan12.api.ApiClient
import com.latihanbyrg.tugaspertemuan12.api.ApiService
import com.latihanbyrg.tugaspertemuan12.databinding.ActivityMainBinding
import com.latihanbyrg.tugaspertemuan12.model.Cat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val listCats by lazy {
        ArrayList<Cat>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Full Screen Window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        } else {
            // Deprecated API BELOW 30
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setContentView(binding.root)

        val client = ApiClient.getInstance()
        fetchData(client)

        // Swipe Refresh Layout
        binding.swipeRefresh.setOnRefreshListener {
            listCats.clear()
            fetchData(client)
            binding.swipeRefresh.isRefreshing = false
        }

        // Button exit
        binding.toolbarContainer.buttonExit.setOnClickListener {
            // Confirmation before finish
            binding.toolbarContainer.buttonExit.setOnClickListener {
                AlertDialog.Builder(this)
                    .setTitle("Exit Application")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes") { _, _ ->
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }


        }



    }

    private fun fetchData(client: ApiService) {
        val breeds = listOf(
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

        breeds.forEach { breed ->
            client.getCat(breed).enqueue(object : Callback<List<Cat>> {
                override fun onResponse(
                    call: Call<List<Cat>>,
                    response: Response<List<Cat>>
                ) {
                    val cats = response.body()
                    if (response.isSuccessful) {
                        if (cats != null) {
                            listCats.addAll(cats)
                            catRecycler(listCats)
                        }
                    } else if (response.code() == 404) {
                        Toast.makeText(this@MainActivity, "Not Found", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    call: Call<List<Cat>>,
                    t: Throwable
                ) {
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    private fun catRecycler(listCats: ArrayList<Cat>) {
        with(binding) {
            rvCards.apply {
                adapter = CatAdapter(listCats)
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }


}