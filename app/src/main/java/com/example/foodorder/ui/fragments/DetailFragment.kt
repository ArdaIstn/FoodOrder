package com.example.foodorder.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodorder.R
import com.example.foodorder.databinding.FragmentDetailBinding
import com.example.foodorder.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val food = args.food
        val foodName = food.yemek_adi
        val foodPrice = food.yemek_fiyat
        val foodImage = food.yemek_resim_adi

        // Ekran ilk yüklendiğinde yiyecek bilgilerini göster
        with(binding) {
            tvDetailName.text = foodName
            tvDetailPrice.text = getString(R.string.currency_format, foodPrice)
            showFoodImage(foodImage, ivDetailPrice)
        }
        observeQuantity()
        observeTotalPrice()

        // Arttırma butonu
        binding.ivInc.setOnClickListener {
            detailViewModel.increaseQuantity(foodPrice)
        }


        // Azaltma butonu
        binding.ivDec.setOnClickListener {
            detailViewModel.decreaseQuantity(foodPrice)
        }

        // Sepete ekleme işlemi
        binding.btnInsert.setOnClickListener {
            if ((detailViewModel.quantity.value ?: 0) > 0) {
                detailViewModel.addToCart(
                    foodName,
                    foodImage,
                    foodPrice,
                    detailViewModel.quantity.value ?: 0,
                    "arda_isitan"
                )

                detailViewModel.isItemAdded.observe(viewLifecycleOwner) { isItemAdded ->
                    if (isItemAdded) {
                        findNavController().navigateUp()
                    }
                }
            }
        }

        return binding.root
    }

    private fun observeQuantity() {
        detailViewModel.quantity.observe(viewLifecycleOwner) { quantity ->
            binding.tvQuantity.text = "$quantity"
        }
    }

    private fun observeTotalPrice() {
        detailViewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.tvTotalPrice.text = getString(R.string.currency_format, totalPrice)
        }
    }


    private fun showFoodImage(imageName: String, imageView: ImageView) {
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/$imageName"
        Glide.with(this).load(url).into(imageView)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}