package com.example.foodorder.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.R
import com.example.foodorder.data.entity.Foods
import com.example.foodorder.databinding.ListItemDesignBinding
import com.example.foodorder.ui.fragments.ListFragmentDirections
import com.example.foodorder.ui.viewmodel.ListViewModel


class FoodDiffCallback : DiffUtil.ItemCallback<Foods>() {
    override fun areItemsTheSame(oldItem: Foods, newItem: Foods): Boolean {
        return oldItem.yemek_id == newItem.yemek_id
    }

    override fun areContentsTheSame(oldItem: Foods, newItem: Foods): Boolean {
        return oldItem == newItem
    }
}

class FoodListAdapter(
    private val listViewModel: ListViewModel, private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Foods, FoodListAdapter.ViewHolder>(FoodDiffCallback()) {

    inner class ViewHolder(private val binding: ListItemDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Foods) {
            var isFav = false
            with(binding) {
                tvName.text = food.yemek_adi
                tvPrice.text =
                    root.context.getString(R.string.food_price, food.yemek_fiyat.toString())
                Glide.with(root.context)
                    .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}")
                    .override(450, 450).into(iv)

                val direction = ListFragmentDirections.listToDetail(food)
                listCardView.setOnClickListener {
                    Navigation.findNavController(it).navigate(direction)
                }

                listViewModel.isFavorite(food.yemek_id).observe(lifecycleOwner) { isFavorite ->
                    ivFav.setImageResource(if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_empty)
                    isFav = isFavorite
                }

                ivFav.setOnClickListener {
                    if (isFav) {
                        ivFav.setImageResource(R.drawable.ic_heart_empty)
                        listViewModel.deleteFavFood(food.yemek_id)
                        isFav = false
                    } else {
                        ivFav.setImageResource(R.drawable.ic_heart_filled)
                        listViewModel.insertFavFood(food)
                        isFav = true
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
