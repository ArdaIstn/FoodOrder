package com.example.foodorder.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.data.entity.Foods
import com.example.foodorder.databinding.ListItemDesignBinding
import com.example.foodorder.ui.fragments.ListFragmentDirections

class FoodListAdapter(private val foodList: List<Foods>) :
    RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListItemDesignBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        holder.binding.tvName.text = food.yemek_adi
        holder.binding.tvPrice.text = "${food.yemek_fiyat}₺"
        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(holder.itemView.context).load(imageUrl).override(490, 490)
            .into(holder.binding.iv)
        val direction = ListFragmentDirections.listToDetail(food)
        holder.binding.listCardView.setOnClickListener {
            Navigation.findNavController(it).navigate(direction)
        }


    }

    override fun getItemCount(): Int {
        return foodList.size
    }

}