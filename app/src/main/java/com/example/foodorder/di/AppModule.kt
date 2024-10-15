package com.example.foodorder.di


import com.example.foodorder.data.datasource.remotedatasource.RemoteFoodDataSource
import com.example.foodorder.data.repository.FoodRepository
import com.example.foodorder.retrofit.FoodsApi
import com.example.foodorder.retrofit.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideRemoteSource(foodsApi: FoodsApi): RemoteFoodDataSource {
        return RemoteFoodDataSource(foodsApi)
    }

    @Provides
    @Singleton
    fun provideRepository(foodsDataSource: RemoteFoodDataSource): FoodRepository {
        return FoodRepository(foodsDataSource)
    }


}