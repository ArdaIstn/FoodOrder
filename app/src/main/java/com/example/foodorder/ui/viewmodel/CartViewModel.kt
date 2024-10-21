package com.example.foodorder.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorder.data.entity.CartFoods
import com.example.foodorder.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


// Burda listelenen yemeklerden istenilen ürünün silme işlemi yapılacak.
// İstenilen adeti silme şeklinde de yapabilirsin.

@HiltViewModel
class CartViewModel @Inject constructor(private val foodsRepository: FoodRepository) : ViewModel() {


    private val _cartFood = MutableLiveData<List<CartFoods>>()
    val cartFood: LiveData<List<CartFoods>>
        get() = _cartFood

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int>
        get() = _totalPrice

    init {
        getFoodsInCart("arda_isitan")
    }

    fun getFoodsInCart(kullanici_adi: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    foodsRepository.getFoodsInCart(kullanici_adi)
                }
                if (response.isSuccessful) {
                    response.body()?.let {
                        _cartFood.value = it.sepet_yemekler
                    }
                    calculateTotalPrice()
                } else {
                    Log.e("getFoodsInCart", "Hata: ${response.message()}")

                }
            } catch (e: Exception) {
                Log.e("getFoodsInCart", "Hata: ${e.message}")
                calculateTotalPrice()

            }
        }
    }

    fun deleteFromCart(sepet_yemek_id: Int, kullanici_adi: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                foodsRepository.deleteFromCart(sepet_yemek_id, kullanici_adi)
            }
            _cartFood.value = _cartFood.value?.filter { it.sepet_yemek_id != sepet_yemek_id }
            calculateTotalPrice()
        }
    }

    // Tüm ürünleri sepetten silen fonksiyon
    fun deleteAllFromCart(kullanici_adi: String) {
        viewModelScope.launch {
            try {
                _cartFood.value?.forEach { food ->
                    withContext(Dispatchers.IO) {
                        foodsRepository.deleteFromCart(food.sepet_yemek_id, kullanici_adi)
                    }
                }
                _cartFood.value = emptyList()  // Tüm ürünler kaldırıldı
                calculateTotalPrice()
            } catch (e: Exception) {
                Log.e("clearCart", "Hata: ${e.message}")
            }
        }
    }

    private fun calculateTotalPrice() {
        val totalPrice = _cartFood.value?.sumOf { it.yemek_siparis_adet * it.yemek_fiyat } ?: 0
        _totalPrice.value = totalPrice
    }


}