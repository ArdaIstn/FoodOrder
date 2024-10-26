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
    }


    fun insertFavFood(food: Foods) {
        viewModelScope.launch {
            foodsRepository.insertFavFoods(food)
        }
    }


    fun deleteFavFood(foodId: Int) {
        viewModelScope.launch {
            foodsRepository.deleteFavFoods(foodId)
        }
    }


    fun isFavorite(food_id: Int): LiveData<Boolean> {
        val isFavoriteLiveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            isFavoriteLiveData.value = foodsRepository.isFavorite(food_id) > 0
        }
        return isFavoriteLiveData
    }
}


