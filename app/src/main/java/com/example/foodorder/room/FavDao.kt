package com.example.foodorder.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodorder.data.entity.Foods

@Dao
interface FavDao {
    @Query("SELECT * FROM Foods")
    suspend fun getFavFoods(): List<Foods>

    @Insert
    suspend fun insertFavFoods(foods: Foods)

    @Query("DELETE FROM Foods WHERE food_id = :foodId")
    suspend fun deleteFavFoods(foodId: Int)

    @Query("SELECT COUNT(*) FROM Foods WHERE food_id = :food_id")
    suspend fun isFavorite(food_id: Int): Int


}