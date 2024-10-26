package com.example.foodorder.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodorder.data.entity.Foods

@Database(entities = [Foods::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun getFavDao(): FavDao

}