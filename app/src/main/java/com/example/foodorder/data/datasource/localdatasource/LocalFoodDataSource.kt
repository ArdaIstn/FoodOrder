package com.example.foodorder.data.datasource.localdatasource

import com.example.foodorder.data.entity.Foods
import com.example.foodorder.room.FavDao

class LocalFoodDataSource(private val favDao: FavDao) {

    suspend fun insertFavFoods(food: Foods) = favDao.insertFavFoods(food)

    suspend fun deleteFavFoods(foodId:Int) = favDao.deleteFavFoods(foodId)

    suspend fun getFavFoods(): List<Foods> = favDao.getFavFoods()

    suspend fun isFavorite(foodId: Int): Int = favDao.isFavorite(foodId)


}