package com.example.foodorder.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Foods")
data class Foods(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "food_id") val yemek_id: Int,
    @ColumnInfo(name = "food_name") val yemek_adi: String,
    @ColumnInfo(name = "food_image_name") val yemek_resim_adi: String,
    @ColumnInfo(name = "food_price") val yemek_fiyat: Int,
) : Serializable