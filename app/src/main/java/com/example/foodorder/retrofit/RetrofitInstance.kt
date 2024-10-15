package com.example.foodorder.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//  http://kasimadalan.pe.hu -> Base URL
object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("http://kasimadalan.pe.hu/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    val api: FoodsApi by lazy {
        retrofit.create(FoodsApi::class.java)
    }


}