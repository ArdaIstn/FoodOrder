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

    private val viewModel: DetailViewModel by viewModels()
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
        binding.tvDetailName.text = foodName
        binding.tvDetailPrice.text = getString(R.string.currency_format, foodPrice)
        showFoodImage(foodImage, binding.ivDetailPrice)

        // ViewModel'deki quantity ve totalPrice gözlemleniyor
        viewModel.quantity.observe(viewLifecycleOwner) { quantity ->
            binding.tvQuantity.text = quantity.toString()
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.tvTotalPrice.text = getString(R.string.currency_format, totalPrice)
        }

        // Arttırma butonu
        binding.btnInc.setOnClickListener {
            viewModel.increaseQuantity(foodPrice)
        }

        // Azaltma butonu
        binding.btnDec.setOnClickListener {
            viewModel.decreaseQuantity(foodPrice)
        }

        // Sepete ekleme işlemi
        binding.btnInsert.setOnClickListener {
            if ((viewModel.quantity.value ?: 0) > 0) {
                viewModel.addToCart(foodName, foodImage, foodPrice, viewModel.quantity.value ?: 0, "ardaa_isitan")
                findNavController().navigateUp()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showFoodImage(imageName: String, imageView: ImageView) {
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/$imageName"
        Glide.with(this).load(url).into(imageView)
    }
}