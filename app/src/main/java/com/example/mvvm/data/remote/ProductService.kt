
package com.example.mvvm.data.remote
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getAllProducts(): Response<ProductResponse>
}