package com.example.jimstore.data

import com.example.jimstore.R
import com.example.jimstore.model.Product

object ProductDataSource {
    val allProducts = listOf(
        Product(
            id = "paracetamol_1",
            nameRes = R.string.paracetamol,
            priceRes = R.string.lkr_250,
            price = 250.0,
            imageRes = R.drawable.paracetamol,
            isFeatured = true,
            isPopular = false
        ),
        Product(
            id = "ibuprofen_1",
            nameRes = R.string.ibuprofen,
            priceRes = R.string.lkr_350,
            price = 350.0,
            imageRes = R.drawable.ibuprofen,
            isFeatured = true
        ),
        Product(
            id = "amoxicillin_1",
            nameRes = R.string.amoxicillin,
            priceRes = R.string.lkr_750,
            price = 750.0,
            imageRes = R.drawable.amoxicilin,
            isFeatured = true
        ),
        Product(
            id = "vitamin_c_1",
            nameRes = R.string.vitamin_c,
            priceRes = R.string.lkr_680,
            price = 680.0,
            imageRes = R.drawable.vitaminc,
            isPopular = true
        ),
        Product(
            id = "omeprazole_1",
            nameRes = R.string.omeprazole,
            priceRes = R.string.lkr_950,
            price = 950.0,
            imageRes = R.drawable.omeprazole,
            isPopular = true
        ),
        Product(
            id = "cetirizine_1",
            nameRes = R.string.cetirizine,
            priceRes = R.string.lkr_420,
            price = 420.0,
            imageRes = R.drawable.cetirizine,
            isPopular = true
        ),
        Product(
            id = "multivitamin_1",
            nameRes = R.string.multivitamin,
            priceRes = R.string.lkr_1200,
            price = 1200.0,
            imageRes = R.drawable.dailymultivitamin
        ),
        Product(
            id = "aspirin_1",
            nameRes = R.string.aspirin,
            priceRes = R.string.lkr_180,
            price = 180.0,
            imageRes = R.drawable.aspirin
        ),
        Product(
            id = "calcium_1",
            nameRes = R.string.calcium,
            priceRes = R.string.lkr_850,
            price = 850.0,
            imageRes = R.drawable.calcium
        ),
        Product(
            id = "cough_syrup_1",
            nameRes = R.string.cough_syrup,
            priceRes = R.string.lkr_320,
            price = 320.0,
            imageRes = R.drawable.cough_syrup
        ),
        Product(
            id = "bandage_1",
            nameRes = R.string.bandage,
            priceRes = R.string.lkr_150,
            price = 150.0,
            imageRes = R.drawable.bandage
        ),
        Product(
            id = "thermometer_1",
            nameRes = R.string.thermometer,
            priceRes = R.string.lkr_1200,
            price = 2500.0,
            imageRes = R.drawable.thermometer
        )
    )

    val featuredProducts: List<Product>
        get() = allProducts.filter { it.isFeatured }

    val popularProducts: List<Product>
        get() = allProducts.filter { it.isPopular }
} 