package com.latihanbyrg.tugaspertemuan12.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihanbyrg.tugaspertemuan12.databinding.ItemCatBinding
import com.latihanbyrg.tugaspertemuan12.model.Cat

class CatAdapter(
    private val cats: ArrayList<Cat>
) : RecyclerView.Adapter<CatAdapter.ItemCatViewHolder>() {
    inner class ItemCatViewHolder(
        private val binding: ItemCatBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Cat) {
            with(binding) {
                tvOrigin.text = data.breeds[0].origin
                tvName.text = data.breeds[0].name
                tvDescription.text = data.breeds[0].description
                tvTemperament.text = data.breeds[0].temperament
                tvLifespan.text = data.breeds[0].lifeSpan
                tvMass.text = data.breeds[0].weight.metric


                // Image Glide
                Glide.with(itemView.context)
                    .load(data.url)
                    .into(binding.imageViewThumbnail)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCatViewHolder {
        val binding = ItemCatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ItemCatViewHolder(binding)
    }

    override fun getItemCount(): Int = cats.size

    override fun onBindViewHolder(holder: ItemCatViewHolder, position: Int) {
        holder.bind(cats[position])
    }

}