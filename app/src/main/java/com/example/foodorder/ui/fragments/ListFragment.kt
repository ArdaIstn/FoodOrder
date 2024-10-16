package com.example.foodorder.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodorder.R
import com.example.foodorder.databinding.FragmentListBinding
import com.example.foodorder.ui.adapter.FoodListAdapter
import com.example.foodorder.ui.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val listViewModel: ListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)



        listViewModel.foodsList.observe(viewLifecycleOwner) { foodList ->
            val foodsAdapter = FoodListAdapter(foodList)
            binding.rv.adapter = foodsAdapter
            binding.rv.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }




        return binding.root


    }


}