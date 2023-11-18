package com.latihanbyrg.tugaspertemuan12

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.latihanbyrg.tugaspertemuan12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var explorerFragment : Fragment
    private lateinit var bookmarkFragment : Fragment
    private lateinit var catViewModel: CatViewModel

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = CatViewModelFactory((application as CatOPiasApplication).repository)
        catViewModel = ViewModelProvider(this, factory)[CatViewModel::class.java]


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
        catViewModel.fetchExplorerData(this)

        explorerFragment = ExplorerFragment()
        bookmarkFragment = BookmarkFragment()


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


        // Bottom Navigation
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.explorerFragment -> {
                    replaceFragment(explorerFragment)
                    true
                }
                R.id.bookmarkFragment -> {
                    replaceFragment(bookmarkFragment)
                    true
                }
                else -> false
            }
        }



    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host, fragment)
            .commit()
    }


}