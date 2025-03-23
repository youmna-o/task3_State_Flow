package com.example.mvvm.data.remote

import android.util.Log
import com.example.mvvm.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRemoteDataSource(private  val service: ProductService) {
    suspend fun getAllProduct(): Flow<List<Product>> {
        val response = service.getAllProducts()
        val productList = response.body()?.products ?: emptyList()
        Log.d("API_RESPONSE", "Response: ${response.body()}")
        return flow { emit(productList) }
    }
}