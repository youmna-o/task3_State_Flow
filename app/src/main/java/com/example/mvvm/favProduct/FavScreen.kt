package com.example.mvvm.favProduct

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mvvm.Response
import com.example.mvvm.allProduct.AllProductViewModel
import com.example.mvvm.allProduct.myFactory
import com.example.mvvm.data.local.ProductDatabase
import com.example.mvvm.data.local.ProductLocalDataSource
import com.example.mvvm.data.model.Product
import com.example.mvvm.data.remote.ProductRemoteDataSource
import com.example.mvvm.data.remote.RetrofitHelper
import com.example.mvvm.data.repo.Repo


class FavScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val factory = myFactory(
                Repo(
                    ProductRemoteDataSource(RetrofitHelper.apiService),
                    ProductLocalDataSource(ProductDatabase.getInstance(this).getProductDao())
                )
            )
            val viewModel = ViewModelProvider(this, factory).get(AllProductViewModel::class.java)
            getAllProduct(viewModel)
        }
    }
}

@Composable
private fun getAllProduct(viewModel: AllProductViewModel) {
    val productState by viewModel.Favproduct
        .collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getFavProducts()
    }

    when (productState) {
        is Response.Loading<*> -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Response.Success<*> -> {
            val products = (productState as Response.Success<List<Product>>).data
            LazyColumn(
                Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(products) { _, currentProduct ->
                  MyItem(currentProduct, viewModel)
                }
            }
        }
        is Response.Failure<*> -> {
            val errorMessage = (productState as Response.Failure<*>).error.message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: $errorMessage", color = Color.Red)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyItem(product: Product, viewModel: AllProductViewModel){
    val currentContext= LocalContext.current
    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.border(4.dp, color = Color.Black, RoundedCornerShape(16.dp)).
        padding(8.dp)
    )
    {
        GlideImage(
            model = product.thumbnail,
            contentDescription = "image",
            Modifier.size(200.dp)
                .border(4.dp, color = Color.Black, shape =  RoundedCornerShape(16.dp))
                .background(color = Color.Gray, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .fillMaxSize()
        )
        Column (Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = product.title,

                color = Color.Gray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
            Text(product.description)
            Button({
                viewModel.deleteFavProducts(product)
            }) { Text("delete") }

        }

    }

}