package com.example.foodorder.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorder.data.entity.Foods
import com.example.foodorder.data.repository.FoodRepository
import com.example.foodorder.retrofit.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Bu viewmodelde api isteği sonucu dönen yemek listesini alıp,ui'da göstereceğim
// Burda önce livedata ile deneyeceğim,sonrasında eğer güncelleme konusunda tekrara düşen bir kod yazarsam,stateflow kullanarak çözeceğim.
// Bu viewmodelin görevi,yemek listesini alıp ui'da göstermektir.
@HiltViewModel
class ListViewModel @Inject constructor(private val foodsRepository: FoodRepository) : ViewModel() {

    private val _foodsList = MutableLiveData<List<Foods>>()
    val foodsList: MutableLiveData<List<Foods>>
        get() = _foodsList

    init {
        fetchFoods()
    }

    private fun fetchFoods() {
        viewModelScope.launch {
            val foodsList = withContext(Dispatchers.IO) {
                foodsRepository.getAllFoods()
            }
            _foodsList.value = foodsList

        }
    }

}