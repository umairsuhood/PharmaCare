package com.example.jimstore.data

import com.example.jimstore.R
import com.example.jimstore.model.Product

object ProductDataSource {
    val allProducts = listOf(
        Product(
            id = "paracetamol_1",
            nameRes = R.string.beauty_deal,
            priceRes = R.string.save_150,
            price = 250.0,
            imageRes = R.drawable.paracetamol,
            isFeatured = true,
            isPopular = false
        ),
        Product(
            id = "ibuprofen_1",
            nameRes = R.string.automotive_deal,
            priceRes = R.string.save_180,
            price = 350.0,
            imageRes = R.drawable.ibuprofen,
            isFeatured = true
        ),
        Product(
            id = "amoxicillin_1",
            nameRes = R.string.books_deal,
            priceRes = R.string.save_180,
            price = 750.0,
            imageRes = R.drawable.amoxicilin,
            isFeatured = true
        ),
        Product(
            id = "vitamin_c_1",
            nameRes = R.string.grocery_deal,
            priceRes = R.string.save_150,
            price = 680.0,
            imageRes = R.drawable.vitaminc,
            isPopular = true
        ),
        Product(
            id = "omeprazole_1",
            nameRes = R.string.electronics_deal,
            priceRes = R.string.save_180,
            price = 950.0,
            imageRes = R.drawable.omeprazole,
            isPopular = true
        ),
        Product(
            id = "cetirizine_1",
            nameRes = R.string.home_deal,
            priceRes = R.string.save_350,
            price = 420.0,
            imageRes = R.drawable.cetirizine,
            isPopular = true
        ),
        Product(
            id = "multivitamin_1",
            nameRes = R.string.restaurant_deal,
            priceRes = R.string.save_750,
            price = 1200.0,
            imageRes = R.drawable.dailymultivitamin
        ),
        Product(
            id = "aspirin_1",
            nameRes = R.string.tech_deal,
            priceRes = R.string.save_950,
            price = 180.0,
            imageRes = R.drawable.aspirin
        ),
        Product(
            id = "calcium_1",
            nameRes = R.string.travel_deal,
            priceRes = R.string.save_950,
            price = 850.0,
            imageRes = R.drawable.calcium
        ),
        Product(
            id = "cough_syrup_1",
            nameRes = R.string.grocery_deal,
            priceRes = R.string.save_180,
            price = 320.0,
            imageRes = R.drawable.cough_syrup
        ),
        Product(
            id = "bandage_1",
            nameRes = R.string.fashion_deal,
            priceRes = R.string.save_750,
            price = 150.0,
            imageRes = R.drawable.bandage
        ),
        Product(
            id = "thermometer_1",
            nameRes = R.string.health_deal,
            priceRes = R.string.save_750,
            price = 2500.0,
            imageRes = R.drawable.thermometer
        )
    )

    val featuredProducts: List<Product>
        get() = allProducts.filter { it.isFeatured }

    val popularProducts: List<Product>
        get() = allProducts.filter { it.isPopular }
} 