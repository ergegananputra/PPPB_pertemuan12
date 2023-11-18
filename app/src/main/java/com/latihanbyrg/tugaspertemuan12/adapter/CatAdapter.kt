package com.latihanbyrg.tugaspertemuan12.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihanbyrg.tugaspertemuan12.R
import com.latihanbyrg.tugaspertemuan12.databinding.ItemCatBinding
import com.latihanbyrg.tugaspertemuan12.model.Cat

typealias OnClickBookmark = (Cat) -> Unit
class CatAdapter(
    private val cats: ArrayList<Cat>,
    private val onClickBookmark: OnClickBookmark
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

                // change button bookmark icon
                if (data.isBookmark) {
                    buttonBookmark.setImageResource(R.drawable.baseline_bookmark_24)
                } else {
                    buttonBookmark.setImageResource(R.drawable.baseline_bookmark_border_24)
                }


                // Image Glide
                Glide.with(itemView.context)
                    .load(data.url)
                    .into(binding.imageViewThumbnail)

                buttonBookmark.setOnClickListener {
                    if (data.isBookmark) {
                        // change button bookmark icon
                        buttonBookmark.setImageResource(R.drawable.baseline_bookmark_border_24)
//                        onClickBookmark(data)
                        data.isBookmark = !data.isBookmark
                    } else {
                        // change button bookmark icon
                        buttonBookmark.setImageResource(R.drawable.baseline_bookmark_24)
                        onClickBookmark(data)
                        data.isBookmark = !data.isBookmark
                    }

                }
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