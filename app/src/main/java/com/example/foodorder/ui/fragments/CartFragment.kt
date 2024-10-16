package com.example.foodorder.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodorder.databinding.FragmentCartBinding
import com.example.foodorder.ui.adapter.CartListAdapter
import com.example.foodorder.ui.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        cartViewModel.cartFood.observe(viewLifecycleOwner) { cartFood ->
            val cartFoodsAdapter = CartListAdapter(cartFood,cartViewModel)
            binding.rvCart.adapter = cartFoodsAdapter
            binding.rvCart.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }

        return binding.root
    }


}