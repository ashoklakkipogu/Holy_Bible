package com.ashok.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.ui.repository.ProductRepository
import com.ashok.myapplication.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(val productRepository: ProductRepository) :
    ViewModel() {

    //stateflow
    private val _products:MutableStateFlow<Result<Products>> = MutableStateFlow(Result.Loading())
    val products: StateFlow<Result<Products>> = _products

    //Livedata
    private val _products1:MutableStateFlow<Result<Products>> = MutableStateFlow(Result.Loading())
    val products1: StateFlow<Result<Products>> = _products1

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getAllProducts().collectLatest {
                _products.value = it
            }
        }
    }
}