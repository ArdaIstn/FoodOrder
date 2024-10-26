package com.example.foodorder.ui.viewmodel

import androidx.lifecycle.*
import com.example.foodorder.data.entity.Foods
import com.example.foodorder.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val foodsRepository: FoodRepository) : ViewModel() {

    private val _foodsList = MutableLiveData<List<Foods>>()
    val foodsList: LiveData<List<Foods>> = _foodsList


    private var allFoods: List<Foods> = listOf()


    init {
        fetchFoods()
    }

    private fun fetchFoods() {
        viewModelScope.launch {
            allFoods = foodsRepository.getAllFoods()
            _foodsList.value = allFoods
        }
    }


    fun filterFoods(query: String) {
        _foodsList.value = if (query.isEmpty()) {
            allFoods
        } else {
            allFoods.filter {
                it.yemek_adi.contains(
                    query, ignoreCase = true
                )
            }
        }
        _foodsList.value = filteredList
    }


}