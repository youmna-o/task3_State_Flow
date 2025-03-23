package com.example.mvvm.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mvvm.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
   /* @Query("SELECT * FROM product")
    suspend fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(products: List<Product>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(products: Product): Long

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product): Int*/
    //coroutines
   @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(products: Product): Long

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product): Int

}