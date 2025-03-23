package com.example.mvvm.data.repo

import android.content.Context
import androidx.room.Room
import com.example.mvvm.data.local.ProductDatabase
import com.example.mvvm.data.local.ProductLocalDataSource
import com.example.mvvm.data.model.Product
import com.example.mvvm.data.remote.ProductRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repo (
    private val remoteDataSource:ProductRemoteDataSource,
    private val localDataSource :ProductLocalDataSource)
{
    suspend fun getAllMovies(isOnline:Boolean):Flow<List<Product>>{
        return remoteDataSource.getAllProduct()

    }
    suspend fun getStoredMovies(isOnline:Boolean):Flow<List<Product>>{
         return   localDataSource.getAll()

    }
    suspend fun insert(product: Product):Long{
           return localDataSource.insertProduct(product)
    }
    suspend fun delete(product: Product):Int{
        return localDataSource.delete(product)

    }
    companion object{
        @Volatile
        private var instance : Repo? = null
        fun getInstance(remoteDataSource: ProductRemoteDataSource,
                        localDataSource: ProductLocalDataSource): Repo {
            return instance ?: synchronized(this){
                val temp= Repo (remoteDataSource,localDataSource)
                instance = temp
                temp
            }

        }
    }

}