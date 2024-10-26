package com.example.foodorder.data.datasource.remotedatasource

import com.example.foodorder.data.entity.CartFoodsResponse
import com.example.foodorder.data.entity.Foods
import com.example.foodorder.retrofit.FoodsApi
import retrofit2.Response

class RemoteFoodDataSource(private val foodsApi: FoodsApi) {

    suspend fun getAllFoods(): List<Foods> = foodsApi.getAllFoods().yemekler

    suspend fun addToCart(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) = foodsApi.addToCart(
        yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi
    )

    suspend fun getFoodsInCart(kullanici_adi: String): Response<CartFoodsResponse> =
        foodsApi.getFoodsInCart(kullanici_adi)

    suspend fun deleteFromCart(sepet_yemek_id: Int, kullanici_adi: String) =
        foodsApi.deleteFromCart(sepet_yemek_id, kullanici_adi)


}