package com.example.mvvm.data.remote

import com.example.mvvm.data.model.Product
import kotlinx.coroutines.flow.Flow

data class ProductResponse(val products: List<Product>)
