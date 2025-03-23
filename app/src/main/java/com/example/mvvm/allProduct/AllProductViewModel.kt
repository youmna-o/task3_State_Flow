package com.example.mvvm.allProduct

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mvvm.Response
import com.example.mvvm.data.model.Product
import com.example.mvvm.data.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AllProductViewModel(private val repo: Repo):ViewModel() {
    /*  private val mutableProducts:MutableLiveData<List<Product>> =MutableLiveData()
    val product:LiveData<List<Product>> = mutableProducts
    private val mutableFavProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val Favproduct: LiveData<List<Product>> = mutableFavProducts
    */
    private val mutableProducts: MutableStateFlow<Response<List<Product>>> = MutableStateFlow(Response.Loading())
    val product: StateFlow<Response<List<Product>>> = mutableProducts.asStateFlow()

    private val mutableFavProducts: MutableStateFlow<Response<List<Product>>> = MutableStateFlow(Response.Loading())
    val Favproduct: StateFlow<Response<List<Product>>> = mutableFavProducts.asStateFlow()

    private val mutableMessage: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = mutableMessage



    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllMovies(true)
                .catch { ex ->
                    mutableProducts.value = Response.Failure(ex)
                }
                .collect { list ->
                    if (list.isEmpty()) {
                    }
                    mutableProducts.value = Response.Success(list)
                }
        }
    }


    fun getFavProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getStoredMovies(false)
                .catch { ex ->
                    mutableFavProducts.value = Response.Failure(ex)
                }
                .collect { list ->
                    if (list.isEmpty()) {
                    }
                    mutableFavProducts.value = Response.Success(list)
                }
        }
    }

    fun addFavProducts(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.insert(product)
                if (result > 0) {
                    mutableMessage.postValue("added")
                } else {
                    mutableMessage.postValue("faild")
                }

            } catch (ex: Exception) {
                mutableMessage.postValue("An erroe ,${ex.message}")
            }

        }

    }

    fun deleteFavProducts(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.delete(product)
                if (result > 0) {
                    mutableMessage.postValue("added")
                    // getFavProducts()
                } else {
                    mutableMessage.postValue("faild")
                }

            } catch (ex: Exception) {
                mutableMessage.postValue("An erroe ,${ex.message}")
            }

        }

    }

}
    class myFactory(private val repo: Repo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllProductViewModel(repo) as T

        }
    }



