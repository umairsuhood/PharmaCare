package com.example.jimstore.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Product(
    val id: String = "",
    val nameRes: Int,
    val priceRes: Int,
    val price: Double = 0.0, // Numeric price for calculations
    val imageRes: Int,
    val isFeatured: Boolean = false,
    val isPopular: Boolean = false
) 

data class CartItem(
    val product: Product,
    val selectedWeight: Float = 2.5f,
    var quantity: Int = 1
) {
    val totalPrice: Double
        get() = product.price * quantity
} 