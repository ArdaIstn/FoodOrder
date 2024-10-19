package com.example.foodorder.retrofit

import com.example.foodorder.data.entity.CRUDResponse
import com.example.foodorder.data.entity.CartFoodsResponse
import com.example.foodorder.data.entity.FoodsResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

// URL : http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php
interface FoodsApi {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getAllFoods(): FoodsResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToCart(
        @Field("yemek_adi") yemek_adi: String,
        @Field("yemek_resim_adi") yemek_resim_adi: String,
        @Field("yemek_fiyat") yemek_fiyat: Int,
        @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
        @Field("kullanici_adi") kullanici_adi: String
    ): CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun getFoodsInCart(
        @Field("kullanici_adi") kullanici_adi: String
    ): Response<CartFoodsResponse>

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteFromCart(
        @Field("sepet_yemek_id") sepet_yemek_id: Int, @Field("kullanici_adi") kullanici_adi: String
    ): CRUDResponse

}