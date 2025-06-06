package com.example.jimstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jimstore.data.ProductDataSource
import com.example.jimstore.model.Product

class HomeViewModel : ViewModel() {
    private val _featuredProducts = MutableLiveData(ProductDataSource.featuredProducts)
    val featuredProducts: LiveData<List<Product>> = _featuredProducts

    private val _popularProducts = MutableLiveData(ProductDataSource.popularProducts)
    val popularProducts: LiveData<List<Product>> = _popularProducts
} 