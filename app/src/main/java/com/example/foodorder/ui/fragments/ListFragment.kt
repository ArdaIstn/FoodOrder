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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val listViewModel: ListViewModel by viewModels()
    private lateinit var listFoodsAdapter: FoodListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFoodsList()
        searchFoods()


    }

    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", false)
        binding.searchView.clearFocus()

    }

    private fun setupRecyclerView() {
        listFoodsAdapter = FoodListAdapter(listViewModel, viewLifecycleOwner)
        binding.rv.apply {
            adapter = listFoodsAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private fun observeFoodsList() {
        listViewModel.foodsList.observe(viewLifecycleOwner) { foodList ->
            listFoodsAdapter.submitList(foodList)
            binding.noData.isVisible = foodList.isEmpty()
        }
    }

    private fun searchFoods() {
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

    }


}