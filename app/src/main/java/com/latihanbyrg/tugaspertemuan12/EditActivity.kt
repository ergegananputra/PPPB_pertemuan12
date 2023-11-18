package com.latihanbyrg.tugaspertemuan12


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.latihanbyrg.tugaspertemuan12.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val catId = intent.getStringExtra(EXTRA_CAT_ID)
        val imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
        val petName = intent.getStringExtra(EXTRA_PET_NAME)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)

        Log.d("EditActivity", "catId: $catId, imageURL: $imageUrl, petName: $petName, description: $description")

        Glide.with(this)
            .load(imageUrl)
            .into(binding.imageViewThumbnail)

        with(binding) {
            editTextNamaPeliharaan.setText(petName)
            editTextDeskripsi.setText(description)


            buttonSimpan.setOnClickListener{
                Log.d("EditActivity", "catId: $catId")
                if (catId != null) {
                    val intent = Intent()
                    intent.putExtra(EXTRA_CAT_ID, catId)
                    intent.putExtra(EXTRA_PET_NAME, editTextNamaPeliharaan.text.toString())
                    intent.putExtra(EXTRA_DESCRIPTION, editTextDeskripsi.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }

            }


        }



    }

    companion object {
        const val EXTRA_CAT_ID = "extra_cat_id"
        const val EXTRA_IMAGE_URL = "extra_image_url"
        const val EXTRA_PET_NAME = "extra_pet_name"
        const val EXTRA_DESCRIPTION = "extra_description"
    }



}