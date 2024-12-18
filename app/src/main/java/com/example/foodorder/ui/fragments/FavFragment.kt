package com.example.foodorder.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodorder.databinding.FragmentFavBinding
import com.example.foodorder.ui.adapter.FavFoodListAdapter
import com.example.foodorder.ui.viewmodel.FavViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment() {
    private lateinit var binding: FragmentFavBinding
    private val favViewModel: FavViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavBinding.inflate(inflater, container, false)

        favViewModel.favFoods.observe(viewLifecycleOwner) { favFoods ->
            val adapter = FavFoodListAdapter(favFoods, favViewModel)
            binding.rvFavList.adapter = adapter
            binding.rvFavList.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.noData.isVisible = favFoods.isEmpty()

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        favViewModel.getFavFoods()
    }
}