package com.example.jimstore.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.outlined.FavoriteBorder
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

@Composable
fun HomeSlideshow() {
    val slides = listOf(
        R.drawable.slides1,
        R.drawable.slides2,
        R.drawable.slides3
    )
    var currentIndex by remember { mutableStateOf(0) }
    val animatedScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "scale"
    )

    LaunchedEffect(key1 = currentIndex) {
        delay(4000)
        currentIndex = (currentIndex + 1) % slides.size
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .scale(animatedScale),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box {
                Image(
                    painter = painterResource(id = slides[currentIndex]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Gradient overlay for better text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.3f)
                                )
                            )
                        )
                )
            }
        }
        
        // Indicator dots
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            slides.forEachIndexed { idx, _ ->
                val animatedSize by animateDpAsState(
                    targetValue = if (idx == currentIndex) 12.dp else 8.dp,
                    animationSpec = tween(300),
                    label = "indicator"
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(animatedSize)
                        .clip(CircleShape)
                        .background(
                            if (idx == currentIndex) 
                                PharmacyGreenAccent 
                            else 
                                Color.White.copy(alpha = 0.7f)
                        )
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    var searchExpanded by remember { mutableStateOf(false) }
    val headerScale by animateFloatAsState(
        targetValue = if (searchExpanded) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "header"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar with Pharmacy Branding
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(headerScale),
                colors = CardDefaults.cardColors(
                    containerColor = PharmacyBluePrimary
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
                        Icon(
                            imageVector = Icons.Filled.LocalPharmacy,
                            contentDescription = "Pharmacy",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "PharmaCare",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Your Health Partner",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                    
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar with Animation
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .clickable { searchExpanded = !searchExpanded },
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
                            tint = PharmacyBluePrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Search medicines, vitamins...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Featured Medicines Section
            ProductSection(
                title = "Featured Medicines",
                subtitle = "Popular treatments",
                products = featuredProducts,
                cartViewModel = cartViewModel,
                onProductClick = { product ->
                    navController.navigate("master/${product.nameRes}/${product.priceRes}/${product.imageRes}")
                }
            )

            // Slideshow between product sections
            HomeSlideshow()

            Spacer(modifier = Modifier.height(24.dp))
            
            // Health Categories Section
            HealthCategoriesSection()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Emergency & Quick Services Section
            EmergencyServicesSection()
            
            Spacer(modifier = Modifier.height(24.dp))

            // Popular Items Section
            ProductSection(
                title = "Popular Items",
                subtitle = "Most ordered products",
                products = popularProducts,
                cartViewModel = cartViewModel,
                onProductClick = { product ->
                    navController.navigate("product_detail/${product.id}")
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Prescription Upload Section
            PrescriptionUploadSection(
                onUploadClick = { /* Handle prescription upload */ }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Health Tips Section
            HealthTipsSection()

            Spacer(modifier = Modifier.height(24.dp))
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
fun ProductSection(
    title: String,
    subtitle: String,
    products: List<Product>,
    cartViewModel: CartViewModel,
    onProductClick: (Product) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            TextButton(
                onClick = { },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = PharmacyBluePrimary
                )
            ) {
                Text(
                    text = "See All",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    cartViewModel = cartViewModel,
                    onClick = { onProductClick(product) }
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    cartViewModel: CartViewModel,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "cardScale"
    )
    
    Card(
        modifier = Modifier
            .width(160.dp)
            .scale(scale)
            .clickable(
                onClick = {
                    isPressed = true
                    onClick()
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                PharmacyLightGray,
                                Color.White
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = stringResource(id = product.nameRes),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(80.dp)
                )
                
                // Favorite icon
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = PharmacyError,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = stringResource(id = product.nameRes),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                minLines = 2
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = product.priceRes),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PharmacyBluePrimary
                )
                
                // Add to Cart Button
                Card(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { 
                            cartViewModel.addToCart(product)
                        },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = PharmacyGreenAccent
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add to Cart",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
    
    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(150)
            isPressed = false
        }
    }
}

@Composable
private fun HealthCategoriesSection() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Shop by Category",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Find medicines by health needs",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(getHealthCategories()) { category ->
                HealthCategoryCard(category = category)
            }
        }
    }
}

@Composable
private fun HealthCategoryCard(category: HealthCategory) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(120.dp)
            .clickable { /* Navigate to category */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = category.backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = category.name,
                        tint = category.iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = category.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun EmergencyServicesSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = PharmacyError.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Emergency,
                    contentDescription = "Emergency",
                    tint = PharmacyError,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Emergency & Quick Services",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EmergencyServiceButton(
                    icon = Icons.Filled.Phone,
                    title = "24/7 Helpline",
                    subtitle = "Call Now",
                    backgroundColor = PharmacyBluePrimary,
                    onClick = { /* Handle call */ }
                )
                
                EmergencyServiceButton(
                    icon = Icons.Filled.LocalShipping,
                    title = "Express Delivery",
                    subtitle = "30 mins",
                    backgroundColor = PharmacyGreenAccent,
                    onClick = { /* Handle express delivery */ }
                )
                
                EmergencyServiceButton(
                    icon = Icons.Filled.VideoCall,
                    title = "Video Consult",
                    subtitle = "With Doctor",
                    backgroundColor = PharmacyError,
                    onClick = { /* Handle video consultation */ }
                )
            }
        }
    }
}

@Composable
private fun EmergencyServiceButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(90.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 12.sp
            )
            
            Text(
                text = subtitle,
                fontSize = 8.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PrescriptionUploadSection(
    onUploadClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = PharmacyBluePrimary.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side - Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CloudUpload,
                        contentDescription = "Upload",
                        tint = PharmacyBluePrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Upload Prescription",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Upload your prescription and get medicines delivered to your doorstep",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Verified",
                        tint = PharmacyGreenAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Verified by licensed pharmacists",
                        fontSize = 12.sp,
                        color = PharmacyGreenAccent,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Right side - Upload button
            Button(
                onClick = onUploadClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PharmacyBluePrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Upload",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Upload",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun HealthTipsSection() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Health Tips & News",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Stay informed about your health",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            TextButton(
                onClick = { /* Navigate to health tips */ },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = PharmacyBluePrimary
                )
            ) {
                Text(
                    text = "View All",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(getHealthTips()) { tip ->
                HealthTipCard(tip = tip)
            }
        }
    }
}

@Composable
private fun HealthTipCard(tip: HealthTip) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(120.dp)
            .clickable { /* Navigate to full article */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = tip.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    lineHeight = 18.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = tip.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    lineHeight = 16.sp
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "Time",
                        tint = PharmacyBluePrimary,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = tip.readTime,
                        fontSize = 10.sp,
                        color = PharmacyBluePrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Icon
            Card(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = tip.iconBackground
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = tip.icon,
                        contentDescription = tip.title,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

// Data classes for the new sections
data class HealthCategory(
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconColor: Color,
    val backgroundColor: Color
)

data class HealthTip(
    val title: String,
    val description: String,
    val readTime: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconBackground: Color
)

// Helper functions to provide data
private fun getHealthCategories(): List<HealthCategory> {
    return listOf(
        HealthCategory(
            name = "Pain Relief",
            icon = Icons.Filled.Healing,
            iconColor = PharmacyError,
            backgroundColor = PharmacyError
        ),
        HealthCategory(
            name = "Vitamins & Supplements",
            icon = Icons.Filled.LocalFlorist,
            iconColor = PharmacyGreenAccent,
            backgroundColor = PharmacyGreenAccent
        ),
        HealthCategory(
            name = "Diabetes Care",
            icon = Icons.Filled.MonitorHeart,
            iconColor = PharmacyBluePrimary,
            backgroundColor = PharmacyBluePrimary
        ),
        HealthCategory(
            name = "Heart Health",
            icon = Icons.Filled.Favorite,
            iconColor = PharmacyError,
            backgroundColor = PharmacyError.copy(alpha = 0.9f)
        ),
        HealthCategory(
            name = "Digestive Health",
            icon = Icons.Filled.Restaurant,
            iconColor = PharmacyGreenAccent,
            backgroundColor = PharmacyGreenAccent.copy(alpha = 0.9f)
        )
    )
}

private fun getHealthTips(): List<HealthTip> {
    return listOf(
        HealthTip(
            title = "5 Ways to Boost Your Immune System",
            description = "Simple daily habits that can strengthen your body's natural defenses...",
            readTime = "3 min read",
            icon = Icons.Filled.Shield,
            iconBackground = PharmacyGreenAccent
        ),
        HealthTip(
            title = "Managing Blood Pressure Naturally",
            description = "Learn about lifestyle changes and dietary tips for healthy blood pressure...",
            readTime = "5 min read",
            icon = Icons.Filled.MonitorHeart,
            iconBackground = PharmacyBluePrimary
        ),
        HealthTip(
            title = "Understanding Your Medication Schedule",
            description = "Tips for remembering to take your medicines on time and safely...",
            readTime = "4 min read",
            icon = Icons.Filled.Schedule,
            iconBackground = PharmacyError
        ),
        HealthTip(
            title = "Seasonal Allergy Prevention",
            description = "How to prepare for allergy season and reduce symptoms naturally...",
            readTime = "3 min read",
            icon = Icons.Filled.Air,
            iconBackground = PharmacyGreenAccent.copy(alpha = 0.8f)
        )
    )
} 