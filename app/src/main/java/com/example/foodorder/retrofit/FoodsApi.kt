package com.example.foodorder.retrofit

import com.example.foodorder.data.entity.CRUDResponse
import com.example.foodorder.data.entity.FoodsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PUT

// URL : http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php
interface FoodsApi {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getAllFoods(): FoodsResponse




}