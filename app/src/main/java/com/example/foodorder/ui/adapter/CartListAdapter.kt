package com.example.foodorder.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.R
import com.example.foodorder.data.entity.CartFoods
import com.example.foodorder.databinding.CartItemDesignBinding
import com.example.foodorder.ui.viewmodel.CartViewModel


class CartListAdapter(private val cartFoodList: List<CartFoods>,private val cartViewModel: CartViewModel) :
    RecyclerView.Adapter<CartListAdapter.ViewHolder>() {

    class ViewHolder(val binding: CartItemDesignBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CartItemDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = cartFoodList[position]
        holder.binding.tvCartName.text = food.yemek_adi
        holder.binding.tvCartPrice.text =
            holder.itemView.context.getString(R.string.cart_food_price, food.yemek_fiyat.toString())
        holder.binding.tvCartNumber.text = holder.itemView.context.getString(
            R.string.cart_food_number,
            food.yemek_siparis_adet.toString()
        )
        holder.binding.ivCartDelete.setOnClickListener {
            cartViewModel.deleteFromCart(food.sepet_yemek_id, food.kullanici_adi)
        }

        holder.binding.tvCartTotalPrice.text = holder.itemView.context.getString(
            R.string.cart_food_total_price, (food.yemek_fiyat * food.yemek_siparis_adet).toString()
        )
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(holder.itemView.context).load(url).into(holder.binding.ivCart)


    }

    override fun getItemCount(): Int {
        return cartFoodList.size
    }

}