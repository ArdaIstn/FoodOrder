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
class ListViewModel @Inject constructor(private val foodsRepository: FoodRepository) : ViewModel() {

    private val _foodsList = MutableLiveData<List<Foods>>()
    val foodsList: MutableLiveData<List<Foods>>
        get() = _foodsList

    private var allFoods: List<Foods> = listOf()


    init {
        fetchFoods()
    }

    private fun fetchFoods() {
        viewModelScope.launch {
            val foodsList = withContext(Dispatchers.IO) {
                foodsRepository.getAllFoods()
            }
            allFoods = foodsList
            _foodsList.value = foodsList

        }
    }


    fun filterFoods(query: String) {
        val filteredList = if (query.isEmpty()) {
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