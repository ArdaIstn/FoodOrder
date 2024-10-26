package com.example.foodorder.data.repository


import com.example.foodorder.data.datasource.localdatasource.LocalFoodDataSource
import com.example.foodorder.data.datasource.remotedatasource.RemoteFoodDataSource
import com.example.foodorder.data.entity.CartFoods
import com.example.foodorder.data.entity.CartFoodsResponse
import com.example.foodorder.data.entity.Foods
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class FoodRepository(
    private val foodsRemotedDataSource: RemoteFoodDataSource,
    private val foodsLocalDataSource: LocalFoodDataSource
) {

    suspend fun getAllFoods(): List<Foods> = foodsRemotedDataSource.getAllFoods()

    suspend fun addToCart(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) = foodsRemotedDataSource.addToCart(
        yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi

    )

    suspend fun getFoodsInCart(kullanici_adi: String): Response<CartFoodsResponse> =
        foodsRemotedDataSource.getFoodsInCart(kullanici_adi)

    suspend fun deleteFromCart(sepet_yemek_id: Int, kullanici_adi: String) =
        foodsRemotedDataSource.deleteFromCart(sepet_yemek_id, kullanici_adi)

    suspend fun getFavFoods(): List<Foods> = foodsLocalDataSource.getFavFoods()

    suspend fun insertFavFoods(food: Foods) = foodsLocalDataSource.insertFavFoods(food)

    suspend fun deleteFavFoods(foodId: Int) = foodsLocalDataSource.deleteFavFoods(foodId)

    suspend fun isFavorite(foodId: Int): Int = foodsLocalDataSource.isFavorite(foodId)


}