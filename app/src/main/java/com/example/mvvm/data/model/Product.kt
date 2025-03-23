
package com.example.mvvm.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val title:String ="",
    val description: String="",
    val price:Double=0.0,
    val thumbnail:String="" )
