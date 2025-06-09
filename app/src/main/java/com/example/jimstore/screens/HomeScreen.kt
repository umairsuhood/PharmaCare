package com.example.jimstore.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jimstore.R
import com.example.jimstore.data.ProductDataSource.featuredProducts
import com.example.jimstore.data.ProductDataSource.popularProducts
import com.example.jimstore.model.Product
import com.example.jimstore.ui.theme.*
import com.example.jimstore.viewmodel.CartViewModel
import kotlinx.coroutines.delay

data class FeaturedStore(
    val name: String,
    val mainOffer: String,
    val subOffer: String,
    val logoResId: Int
)

data class PopularStore(
    val name: String,
    val category: String,
    val offer: String,
    val logoResId: Int
)

@Composable
fun HomeScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    
    // Sample data for deals from various businesses
    val bannerDeals = listOf(
        Triple("Flash Sale at Amazon - Up to 70% OFF", DealHot, Icons.Filled.FlashOn),
        Triple("Best Buy Weekend Special - Buy 2 Get 1 FREE", DealPurpleAccent, Icons.Filled.Weekend),
        Triple("Target Limited Time - Extra 50% OFF", DealSuccess, Icons.Filled.LocalOffer),
        Triple("Walmart Mega Sale - Save Big Today!", DealWarning, Icons.Filled.Sell)
    )
    
    val categories = listOf(
        "All", "Electronics", "Fashion", "Home", "Beauty", "Sports", "Books"
    )
    
    // Featured Outlets/Stores instead of products
    val featuredOutlets = listOf(
        FeaturedStore("Amazon", "Up to 70% OFF Electronics", "Free shipping on orders $25+", R.drawable.amazon),
        FeaturedStore("Best Buy", "Tech Deals & Price Match", "Geek Squad services available", R.drawable.bestbuy),
        FeaturedStore("Target", "Everyday Essentials", "5% off with RedCard", R.drawable.target),
        FeaturedStore("Walmart", "Rollback Prices", "Free pickup & delivery", R.drawable.walmart),
        FeaturedStore("Costco", "Bulk Savings", "Members-only exclusive deals", R.drawable.costco)
    )
    
    // Popular Stores instead of trending deals
    val popularStores = listOf(
        PopularStore("Pizza Hut", "Large Pizza Combo Deals", "Up to 40% OFF", R.drawable.pizzahut),
        PopularStore("Macy's", "Fashion & Beauty", "Extra 30% OFF sale items", R.drawable.macys),
        PopularStore("Best Buy", "Electronics & Appliances", "Price match guarantee", R.drawable.bestbuy),
        PopularStore("Target", "Household Essentials", "Buy 2 Get 1 FREE", R.drawable.target)
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Top Header with Search
                Column {
            Card(
                        modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                            containerColor = DealOrangePrimary
                ),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            // Header Row
                Row(
                                modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                        Column {
                            Text(
                                        text = "Welcome to",
                                        fontSize = 16.sp,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                    Text(
                                        text = "DealHunter",
                                        fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                        text = "Find the best deals from top stores",
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                }
                                
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Card(
                                        modifier = Modifier.size(40.dp),
                                        shape = CircleShape,
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White.copy(alpha = 0.2f)
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                                            Icon(
                                                imageVector = Icons.Filled.Notifications,
                                                contentDescription = "Deal Alerts",
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                    
                Card(
                                        modifier = Modifier.size(40.dp),
                                        shape = CircleShape,
                    colors = CardDefaults.cardColors(
                                            containerColor = Color.White.copy(alpha = 0.2f)
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                    ) {
                        Icon(
                                                imageVector = Icons.Filled.Person,
                                                contentDescription = "Profile",
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            // Search Bar
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { 
                                    Text(
                                        "Search deals from all stores...",
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "Search",
                                        tint = Color.White.copy(alpha = 0.8f)
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Tune,
                                        contentDescription = "Filter Stores",
                                        tint = Color.White.copy(alpha = 0.8f),
                                        modifier = Modifier.clickable { /* Handle filter */ }
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.White.copy(alpha = 0.5f),
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                                    cursorColor = Color.White,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            item {
                // Banner Slider
                var currentBannerIndex by remember { mutableStateOf(0) }
                val bannerListState = rememberLazyListState()
                
                LaunchedEffect(currentBannerIndex) {
                    while (true) {
                        delay(3000)
                        currentBannerIndex = (currentBannerIndex + 1) % bannerDeals.size
                        bannerListState.animateScrollToItem(currentBannerIndex)
                    }
                }
                
                Column {
                    LazyRow(
                        state = bannerListState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(bannerDeals.size) { index ->
                            val (text, color, icon) = bannerDeals[index]
    Card(
        modifier = Modifier
                                    .width(320.dp)
                                    .fillMaxHeight(),
                                shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
                                    containerColor = color
                                )
        ) {
            Box(
                modifier = Modifier
                                        .fillMaxSize()
                    .background(
                                            Brush.horizontalGradient(
                            colors = listOf(
                                                    Color.Black.copy(alpha = 0.3f),
                                                    Color.Transparent
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                Icon(
                                            imageVector = icon,
                                            contentDescription = "Deal Icon",
                                            tint = Color.White,
                                            modifier = Modifier.size(48.dp)
                                        )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                                            text = text,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        )
            Spacer(modifier = Modifier.height(8.dp))
                                        Button(
                                            onClick = { /* Handle banner click */ },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.White.copy(alpha = 0.9f)
                                            ),
                                            shape = RoundedCornerShape(20.dp)
                                        ) {
                                            Text(
                                                text = "View Deals",
                                                color = Color.Black,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Page Indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(bannerDeals.size) { index ->
                            val isSelected = currentBannerIndex == index
                            Box(
                                modifier = Modifier
                                    .size(if (isSelected) 12.dp else 8.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected) DealOrangePrimary else DealLightGray
                                    )
                            )
                            if (index < bannerDeals.size - 1) {
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            }

            item {
                // Categories Section
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                            text = "Deal Categories",
                            fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        
                        TextButton(
                            onClick = { /* Handle see all */ }
                        ) {
                            Text(
                                text = "See All Stores",
                                color = DealOrangePrimary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(categories) { category ->
                            CategoryChip(
                                category = category,
                                isSelected = selectedCategory == category,
                                onClick = { selectedCategory = category }
                            )
                        }
                    }
                }
            }

            item {
                // Featured Outlets Section
                Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Store,
                                contentDescription = "Featured Outlets",
                                tint = DealHot,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                Text(
                                text = "Featured Outlets",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                        }
                        
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = DealHot
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                Text(
                                text = "Hot Deals",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
        
        LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(featuredOutlets) { store ->
                            FeaturedOutletCard(
                                storeName = store.name,
                                mainOffer = store.mainOffer,
                                subOffer = store.subOffer,
                                logoResId = store.logoResId,
                                onClick = { /* Handle outlet click */ }
                            )
                        }
                    }
                }
            }

            item {
                // Quick Actions
                Column {
                    Text(
                        text = "Quick Actions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickActionCard(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Filled.LocalOffer,
                            title = "Today's Deals",
                            subtitle = "50+ stores",
                            backgroundColor = DealSuccess.copy(alpha = 0.1f),
                            iconColor = DealSuccess,
                            onClick = { /* Handle today's deals */ }
                        )
                        
                        QuickActionCard(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Filled.Percent,
                            title = "Coupons",
                            subtitle = "All stores",
                            backgroundColor = DealPurpleAccent.copy(alpha = 0.1f),
                            iconColor = DealPurpleAccent,
                            onClick = { /* Handle coupons */ }
                        )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickActionCard(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Filled.Bookmark,
                            title = "Saved Deals",
                            subtitle = "Your favorites",
                            backgroundColor = DealHot.copy(alpha = 0.1f),
                            iconColor = DealHot,
                            onClick = { /* Handle saved deals */ }
                        )
                        
                        QuickActionCard(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Filled.Store,
                            title = "All Stores",
                            subtitle = "Browse stores",
                            backgroundColor = DealWarning.copy(alpha = 0.1f),
                            iconColor = DealWarning,
                            onClick = { /* Handle all stores */ }
                        )
                    }
                }
            }

            item {
                // Popular Stores Section
                Column {
                    Row(
        modifier = Modifier
            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                                imageVector = Icons.Filled.TrendingUp,
                                contentDescription = "Popular Stores",
                                tint = DealPurpleAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                                text = "Popular Stores",
                                fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        
                        TextButton(
                            onClick = { navController.navigate("products") }
                        ) {
                            Text(
                                text = "View All",
                                color = DealOrangePrimary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        popularStores.forEach { store ->
                            PopularStoreCard(
                                storeName = store.name,
                                category = store.category,
                                offer = store.offer,
                                logoResId = store.logoResId,
                                onClick = { /* Handle store click */ }
                            )
                        }
                    }
                }
            }

            item {
                // Bottom Spacing
                Spacer(modifier = Modifier.height(20.dp))
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
private fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) DealOrangePrimary else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        )
    ) {
        Text(
            text = category,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun FeaturedOutletCard(
    storeName: String,
    mainOffer: String,
    subOffer: String,
    logoResId: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Store Logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        DealLightGray,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = logoResId),
                    contentDescription = "Store Logo",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop
                )
                
                // Hot Deal Badge
                Card(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DealHot
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
            Text(
                        text = "HOT",
                        color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
                    Text(
                text = storeName,
                fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                
            Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                text = mainOffer,
                    fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = DealOrangePrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
                    Text(
                text = subOffer,
                        fontSize = 12.sp,
                color = DealMidGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
                    )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Visit Store Button
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DealOrangePrimary
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                        imageVector = Icons.Filled.OpenInNew,
                        contentDescription = "Visit Store",
                    tint = Color.White,
                        modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                        text = "Visit Store",
                    color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                )
                }
            }
        }
    }
}

@Composable
private fun PopularStoreCard(
    storeName: String,
    category: String,
    offer: String,
    logoResId: Int,
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
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Store Logo
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        DealLightGray,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = logoResId),
                    contentDescription = "Store Logo",
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Crop
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = storeName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = category,
                    fontSize = 14.sp,
                    color = DealMidGray,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = offer,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = DealOrangePrimary
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                // Popular Badge
    Card(
        colors = CardDefaults.cardColors(
                        containerColor = DealSuccess
                    ),
                    shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                        text = "POPULAR",
                        color = Color.White,
                        fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Visit Store Button
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DealOrangePrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                            imageVector = Icons.Filled.OpenInNew,
                            contentDescription = "Visit Store",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                            text = "Visit Store",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    backgroundColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
            Card(
        modifier = modifier
            .height(80.dp)
            .clickable { onClick() },
                colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
} 