package com.example.jimstore.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.jimstore.model.CartItem
import com.example.jimstore.model.Product

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _subtotal = MutableStateFlow(0.0)
    val subtotal: StateFlow<Double> = _subtotal.asStateFlow()

    private val _deliveryFee = MutableStateFlow(1000.0)
    val deliveryFee: StateFlow<Double> = _deliveryFee.asStateFlow()

    private val _discount = MutableStateFlow(0.0)
    val discount: StateFlow<Double> = _discount.asStateFlow()

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total.asStateFlow()

    fun addToCart(product: Product, selectedWeight: Float = 2.5f) {
        val currentItems = _cartItems.value.toMutableList()
        
        // Check if item already exists in cart
        val existingItemIndex = currentItems.indexOfFirst { 
            it.product.id == product.id && it.selectedWeight == selectedWeight 
        }
        
        if (existingItemIndex != -1) {
            // Increase quantity if item already exists
            currentItems[existingItemIndex] = currentItems[existingItemIndex].copy(
                quantity = currentItems[existingItemIndex].quantity + 1
            )
        } else {
            // Add new item
            currentItems.add(CartItem(product, selectedWeight, 1))
        }
        
        _cartItems.value = currentItems
        updateTotals()
    }

    fun removeFromCart(cartItem: CartItem) {
        val currentItems = _cartItems.value.toMutableList()
        currentItems.remove(cartItem)
        _cartItems.value = currentItems
        updateTotals()
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
            return
        }
        
        val currentItems = _cartItems.value.toMutableList()
        val index = currentItems.indexOf(cartItem)
        if (index != -1) {
            currentItems[index] = cartItem.copy(quantity = newQuantity)
            _cartItems.value = currentItems
            updateTotals()
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        updateTotals()
    }

    fun getCartItemCount(): Int {
        return _cartItems.value.sumOf { it.quantity }
    }

    private fun updateTotals() {
        val subtotalValue = _cartItems.value.sumOf { it.totalPrice }
        _subtotal.value = subtotalValue
        _total.value = subtotalValue + _deliveryFee.value - _discount.value
    }
} 