package com.latihanbyrg.tugaspertemuan12.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihanbyrg.tugaspertemuan12.R
import com.latihanbyrg.tugaspertemuan12.database.model.CatTable
import com.latihanbyrg.tugaspertemuan12.databinding.ItemCatBinding
import com.latihanbyrg.tugaspertemuan12.model.Cat


typealias CatListener = (CatTable) -> Unit
class CatTableAdapter (
    private val cats: List<CatTable>,
    private val onLongPressCard: CatListener,
    private val onClickBookmark: CatListener
) : RecyclerView.Adapter<CatTableAdapter.ItemCatViewHolder>() {
    inner class ItemCatViewHolder(
        private val binding: ItemCatBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CatTable) {
            with(binding) {
                tvOrigin.text = data.origin
                tvName.text = data.name
                tvDescription.text = data.description
                tvTemperament.text = data.temperament
                tvLifespan.text = data.lifeSpan
                tvMass.text = data.weight

                // tv_pet_name
                tvPetName.text = data.petName
                tvPetName.visibility = android.view.View.VISIBLE

                // change button bookmark icon
                buttonBookmark.setImageResource(R.drawable.baseline_bookmark_24)


                // Image Glide
                Glide.with(itemView.context)
                    .load(data.url)
                    .into(binding.imageViewThumbnail)

                // long press card
                cardViewCat.setOnLongClickListener {
                    onLongPressCard(data)
                    true
                }


                buttonBookmark.setOnClickListener {
                    // change button bookmark icon
                    buttonBookmark.setImageResource(R.drawable.baseline_bookmark_border_24)
                    notifyItemChanged(adapterPosition)


                    onClickBookmark(data)
                    notifyItemRemoved(adapterPosition)
                }

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCatViewHolder {
        val binding = ItemCatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemCatViewHolder(binding)
    }

    override fun getItemCount(): Int = cats.size

    override fun onBindViewHolder(holder: ItemCatViewHolder, position: Int) {
        holder.bind(cats[position])
    }
}