package com.example.foodorder.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodorder.databinding.FragmentListBinding
import com.example.foodorder.ui.adapter.FoodListAdapter
import com.example.foodorder.ui.viewmodel.ListViewModel
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
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
            with(binding) {
                rv.adapter = foodsAdapter
                rv.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                noData.isVisible = foodList.isEmpty()
            }
        }

        binding.searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    listViewModel.filterFoods(it)
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    listViewModel.filterFoods(it)
                }
                return true
            }
        })

        return binding.root


    }

    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", false)
        binding.searchView.clearFocus()

    }


}