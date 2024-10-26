package com.example.foodorder.di


import android.content.Context
import androidx.room.Room
import com.example.foodorder.data.datasource.localdatasource.LocalFoodDataSource
import com.example.foodorder.data.datasource.remotedatasource.RemoteFoodDataSource
import com.example.foodorder.data.repository.FoodRepository
import com.example.foodorder.retrofit.FoodsApi
import com.example.foodorder.retrofit.RetrofitInstance
import com.example.foodorder.room.DataBase
import com.example.foodorder.room.FavDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): FoodsApi {
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(foodsApi: FoodsApi): RemoteFoodDataSource {
        return RemoteFoodDataSource(foodsApi)
    }

    @Provides
    @Singleton
    fun provideFavDao(@ApplicationContext context: Context): FavDao {
        val db = Room.databaseBuilder(context, DataBase::class.java, "fav_db").build()
        return db.getFavDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(favDao: FavDao): LocalFoodDataSource {
        return LocalFoodDataSource(favDao)
    }

    @Provides
    @Singleton
    fun provideRepository(
        remoteFoodsDataSource: RemoteFoodDataSource, localFoodsDataSource: LocalFoodDataSource
    ): FoodRepository {
        return FoodRepository(remoteFoodsDataSource, localFoodsDataSource)
    }


}