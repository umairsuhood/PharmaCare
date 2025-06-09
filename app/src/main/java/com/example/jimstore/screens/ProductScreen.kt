package com.example.jimstore.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jimstore.R
import com.example.jimstore.ui.theme.*
import com.example.jimstore.viewmodel.CartViewModel

data class Deal(
    val id: String,
    val title: String,
    val store: String,
    val originalPrice: String,
    val salePrice: String,
    val discount: String,
    val category: String,
    val description: String,
    val expiresIn: String,
    val imageRes: Int,
    val isHot: Boolean = false,
    val isFlash: Boolean = false
)

@Composable
fun ProductScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("All Deals") }
    var sortBy by remember { mutableStateOf("Best Deals") }
    
    val categories = listOf("All Deals", "Electronics", "Fashion", "Home & Garden", "Beauty", "Sports", "Books")
    val sortOptions = listOf("Best Deals", "Highest Discount", "Price: Low to High", "Ending Soon", "Most Popular")
    
    // Sample deals data with images
    val allDeals = listOf(
        Deal("1", "MacBook Air M2", "Best Buy", "$1,299", "$999", "23% OFF", "Electronics", "Latest Apple MacBook Air with M2 chip", "2 days left", R.drawable.laptop, isHot = true),
        Deal("3", "Samsung 55\" 4K TV", "Amazon", "$799", "$549", "31% OFF", "Electronics", "Crystal clear 4K display with smart features", "1 day left", R.drawable.tv, isHot = true),

        Deal("5", "iPhone 14 Pro", "Verizon", "$999", "$799", "20% OFF", "Electronics", "Latest iPhone with Pro camera system", "6 hours left", R.drawable.phone, isFlash = true),
        Deal("6", "Levi's 501 Jeans", "Macy's", "$69", "$39", "43% OFF", "Fashion", "Classic straight-leg denim jeans", "2 days left", R.drawable.jeans),
        Deal("7", "KitchenAid Mixer", "Williams Sonoma", "$379", "$279", "26% OFF", "Home & Garden", "Professional stand mixer for baking", "4 days left", R.drawable.mixer),
        Deal("8", "Sephora Beauty Set", "Sephora", "$120", "$79", "34% OFF", "Beauty", "Complete skincare and makeup collection", "1 day left", R.drawable.beauty, isHot = true),
        Deal("9", "PlayStation 5", "GameStop", "$499", "$449", "10% OFF", "Electronics", "Latest gaming console with exclusive games", "8 hours left", R.drawable.gaming, isFlash = true),
        Deal("10", "Adidas Ultraboost", "Adidas", "$180", "$108", "40% OFF", "Sports", "Premium running shoes with boost technology", "3 days left", R.drawable.running_shoes),

    )
    
    val filteredDeals = allDeals.filter { deal ->
        if (selectedCategory == "All Deals") true
        else deal.category == selectedCategory
    }.filter { deal ->
        if (searchQuery.isBlank()) true
        else deal.title.contains(searchQuery, ignoreCase = true) || 
             deal.store.contains(searchQuery, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            // Top Bar with Deal Branding
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = DealOrangePrimary
                ),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Icon(
                            imageVector = Icons.Filled.LocalOffer,
                            contentDescription = "Deals",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "All Deals",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "${filteredDeals.size} amazing deals",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { showFilters = !showFilters }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FilterList,
                                contentDescription = "Filter",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        IconButton(
                            onClick = { /* Handle sort */ }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Sort,
                                contentDescription = "Sort",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = DealOrangePrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                "Search deals, stores, products...",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Category Filter Chips
            AnimatedVisibility(
                visible = showFilters,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Column {
                    LazyColumn {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                categories.take(3).forEach { category ->
                                    FilterChip(
                                        onClick = { selectedCategory = category },
                                        label = {
                                            Text(
                                                text = category,
                                                fontSize = 12.sp
                                            )
                                        },
                                        selected = selectedCategory == category,
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = DealOrangePrimary,
                                            selectedLabelColor = Color.White,
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                categories.drop(3).forEach { category ->
                                    FilterChip(
                                        onClick = { selectedCategory = category },
                                        label = {
                                            Text(
                                                text = category,
                                                fontSize = 12.sp
                                            )
                                        },
                                        selected = selectedCategory == category,
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = DealOrangePrimary,
                                            selectedLabelColor = Color.White,
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            // Deals List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredDeals) { deal ->
                    DealCard(
                        deal = deal,
                        onClick = { /* Handle deal click */ }
                    )
                }
            }
        }

        // Bottom Navigation Bar
        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            navController = navController,
            cartViewModel = cartViewModel
        )
    }
}

@Composable
fun DealCard(
    deal: Deal,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Deal Image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        DealLightGray,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = deal.imageRes),
                    contentDescription = deal.title,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
                
                // Deal badges
                if (deal.isHot) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (-4).dp),
                        colors = CardDefaults.cardColors(
                            containerColor = DealHot
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "HOT",
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
                
                if (deal.isFlash) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = (-4).dp, y = (-4).dp),
                        colors = CardDefaults.cardColors(
                            containerColor = DealFlash
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FlashOn,
                                contentDescription = "Flash",
                                tint = Color.White,
                                modifier = Modifier.size(8.dp)
                            )
                            Text(
                                text = "FLASH",
                                color = Color.White,
                                fontSize = 6.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Deal Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Store name
                Text(
                    text = deal.store,
                    fontSize = 12.sp,
                    color = DealOrangePrimary,
                    fontWeight = FontWeight.Medium
                )
                
                // Deal title
                Text(
                    text = deal.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Description
                Text(
                    text = deal.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Price and discount
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = deal.salePrice,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DealOrangePrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = deal.originalPrice,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = DealSuccess
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(24.dp)
                                .padding(horizontal = 6.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = deal.discount,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Expires in
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "Time",
                        tint = DealWarning,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Expires in ${deal.expiresIn}",
                        fontSize = 10.sp,
                        color = DealWarning,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Get Deal Button
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DealOrangePrimary
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.OpenInNew,
                        contentDescription = "Get Deal",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Get Deal",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
} 