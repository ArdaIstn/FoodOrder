package com.example.foodorder.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.R
import com.example.foodorder.data.entity.Foods
import com.example.foodorder.databinding.ListItemDesignBinding
import com.example.foodorder.ui.viewmodel.FavViewModel
import com.google.android.material.snackbar.Snackbar

class FavFoodListAdapter(
    private val favFoodList: List<Foods>, private val favViewModel: FavViewModel
) : RecyclerView.Adapter<FavFoodListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListItemDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            ListItemDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = favFoodList[position]
        holder.binding.tvName.text = food.yemek_adi
        holder.binding.tvPrice.text =
            holder.itemView.context.getString(R.string.food_price, food.yemek_fiyat.toString())
        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(holder.itemView.context).load(imageUrl).override(450, 450)
            .into(holder.binding.iv)

        holder.binding.ivFav.setImageResource(R.drawable.ic_heart_filled)
        holder.binding.ivFav.setOnClickListener {
            favViewModel.deleteFavFood(food.yemek_id)
            Snackbar.make(it, "${food.yemek_adi} favorilerden çıkarıldı", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun getItemCount(): Int {
        return favFoodList.size
    }


}

