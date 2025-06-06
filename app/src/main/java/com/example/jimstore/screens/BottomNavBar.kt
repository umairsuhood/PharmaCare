package com.example.jimstore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jimstore.navigation.Routes
import com.example.jimstore.viewmodel.CartViewModel
import androidx.compose.foundation.clickable

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier, 
    navController: NavController? = null,
    cartViewModel: CartViewModel? = null
) {
    val nav = navController
    val navBackStackEntry = nav?.currentBackStackEntryAsState()?.value
    val currentRoute = navBackStackEntry?.destination?.route
    val cartItemCount = cartViewModel?.getCartItemCount() ?: 0

    val items = listOf(
        Triple(Icons.Filled.Home, Routes.HOME, "Home"),
        Triple(Icons.Filled.Search, Routes.PRODUCTS, "Products"),
        Triple(Icons.Filled.ShoppingCart, Routes.CART, "Cart"),
        Triple(Icons.Filled.Person, Routes.PROFILE, "Profile")
    )
    val selectedTab = items.indexOfFirst { it.second == currentRoute }.let { if (it == -1) 0 else it }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                BottomNavItem(
                    icon = item.first,
                    isSelected = selectedTab == index,
                    showBadge = item.second == Routes.CART && cartItemCount > 0,
                    badgeCount = cartItemCount,
                    onClick = {
                        if (nav != null && currentRoute != item.second) {
                            nav.navigate(item.second) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(Routes.HOME) { saveState = true }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    isSelected: Boolean,
    showBadge: Boolean = false,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    val iconColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.surfaceVariant
                else Color.Transparent
            )
            .padding(12.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor
        )
        
        // Badge for cart items
        if (showBadge) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Red, CircleShape)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (badgeCount > 99) "99+" else badgeCount.toString(),
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
} 