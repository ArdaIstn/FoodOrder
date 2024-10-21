package com.example.foodorder.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorder.data.entity.CartFoods
import com.example.foodorder.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Bu viewmodelda,öncelikle seçilen adete göre yemek ekleme işlemini yapacağım.
// İlgili apiye yemek ekleme isteği yollanır.

@HiltViewModel
class DetailViewModel @Inject constructor(val foodRepository: FoodRepository) : ViewModel() {

    private var _quantity = MutableLiveData(0)
    val quantity: LiveData<Int> get() = _quantity

    private var _totalPrice = MutableLiveData(0)
    val totalPrice: LiveData<Int> get() = _totalPrice

    private val _isItemAdded = MutableLiveData<Boolean>()
    val isItemAdded: LiveData<Boolean> get() = _isItemAdded

    fun increaseQuantity(foodPrice: Int) {
        _quantity.value = (_quantity.value ?: 0) + 1
        calculateTotalPrice(foodPrice)
    }

    fun decreaseQuantity(foodPrice: Int) {
        if ((_quantity.value ?: 0) > 0) {
            _quantity.value = (_quantity.value ?: 0) - 1
            calculateTotalPrice(foodPrice)
        }
    }

    private fun calculateTotalPrice(foodPrice: Int) {
        _totalPrice.value = (_quantity.value ?: 0) * foodPrice
    }

    fun addToCart(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) {
        viewModelScope.launch {
            var cartFood: CartFoods? = null
            try {
                val response = withContext(Dispatchers.IO) {
                    foodRepository.getFoodsInCart(kullanici_adi)
                }
                for (it in response.body()?.sepet_yemekler!!) {
                    if (it.yemek_adi == yemek_adi) {
                        cartFood = it
                        break
                    }
                }
                if (cartFood != null) {
                    val newQuantity = cartFood.yemek_siparis_adet + yemek_siparis_adet
                    foodRepository.deleteFromCart(cartFood.sepet_yemek_id, kullanici_adi)
                    foodRepository.addToCart(
                        yemek_adi, yemek_resim_adi, yemek_fiyat, newQuantity, kullanici_adi
                    )
                } else {
                    foodRepository.addToCart(
                        yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi
                    )
                }
                _isItemAdded.value = true

            } catch (e: Exception) {
                foodRepository.addToCart(
                    yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi
                )
                _isItemAdded.value = true
                e.printStackTrace()
            }
        }
    }
}



