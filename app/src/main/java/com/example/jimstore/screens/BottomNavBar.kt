package com.example.jimstore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jimstore.ui.theme.*
import com.example.jimstore.viewmodel.CartViewModel

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    cartViewModel: CartViewModel
) {
    var selectedTab by remember { mutableStateOf("home") }
    val savedDealsCount by cartViewModel.cartItems.collectAsState()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home
            BottomNavItem(
                icon = Icons.Filled.Home,
                label = "Home",
                isSelected = selectedTab == "home",
                onClick = {
                    selectedTab = "home"
                    navController.navigate("home")
                }
            )
            
            // Deals
            BottomNavItem(
                icon = Icons.Filled.LocalOffer,
                label = "Deals",
                isSelected = selectedTab == "deals",
                onClick = {
                    selectedTab = "deals"
                    navController.navigate("products")
                }
            )

            
            // Saved Deals (previously Cart)
            BottomNavItem(
                icon = Icons.Filled.Bookmark,
                label = "Saved",
                isSelected = selectedTab == "saved",
                badgeCount = savedDealsCount.size,
                onClick = {
                    selectedTab = "saved"
                    navController.navigate("cart")
                }
            )
            
            // Profile
            BottomNavItem(
                icon = Icons.Filled.Person,
                label = "Profile",
                isSelected = selectedTab == "profile",
                onClick = {
                    selectedTab = "profile"
                    navController.navigate("profile")
                }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) DealOrangePrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            
            // Badge for saved deals count
            if (badgeCount > 0) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .background(DealHot, CircleShape)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (badgeCount > 99) "99+" else badgeCount.toString(),
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) DealOrangePrimary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
} 