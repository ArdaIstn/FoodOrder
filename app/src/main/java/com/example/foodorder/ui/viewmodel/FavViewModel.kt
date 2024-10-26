package com.example.foodorder.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorder.data.entity.Foods
import com.example.foodorder.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class FavViewModel @Inject constructor(private val foodRepository: FoodRepository) : ViewModel() {
    private val _favFoods = MutableLiveData<List<Foods>>()
    val favFoods: MutableLiveData<List<Foods>> = _favFoods

    init {
        getFavFoods()
    }

    fun getFavFoods() {
        viewModelScope.launch {
            val favFoods = withContext(Dispatchers.IO) {
                foodRepository.getFavFoods()
            }
            _favFoods.value = favFoods
        }
    }

    fun deleteFavFood(foodId: Int) {
        viewModelScope.launch {
            foodRepository.deleteFavFoods(foodId)
        }
        _favFoods.value = _favFoods.value?.filter { it.yemek_id != foodId }
    }


}