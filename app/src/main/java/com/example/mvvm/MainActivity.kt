package com.example.mvvm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.example.mvvm.data.model.Product
import com.example.mvvm.data.local.ProductDatabase
import com.example.mvvm.allProduct.AllProductsScreen
import com.example.mvvm.favProduct.FavScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity() : ComponentActivity() {

    val context =this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainPage()

        }

    }

@Composable
private fun MainPage() {
    val currentContext = LocalContext.current
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button({
            val intent = Intent(currentContext, AllProductsScreen::class.java).apply {}
            currentContext.startActivity(intent)
        }) {
            Text("ALl Products")
        }
        Button({
            val intent = Intent(currentContext, FavScreen::class.java).apply {}
            currentContext.startActivity(intent)
        }) {
            Text("Fav Products")
        }
    }

}
}



