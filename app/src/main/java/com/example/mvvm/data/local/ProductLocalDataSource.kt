package com.example.mvvm.data.local

import com.example.mvvm.data.model.Product
import kotlinx.coroutines.flow.Flow

class ProductLocalDataSource (private val dao:ProductDao){
    suspend fun getAll(): Flow<List<Product>> {
        return  dao.getAll()
    }
    suspend fun insertProduct(product: Product):Long{
        return  dao.insertProduct(product)
    }
    suspend fun delete(product: Product):Int{
        return if(product!=null)
        dao.delete(product)
        else{
            -1
        }
    }
}