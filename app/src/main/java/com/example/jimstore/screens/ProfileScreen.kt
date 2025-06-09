package com.example.jimstore.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jimstore.R
import com.example.jimstore.ui.theme.*
import com.example.jimstore.viewmodel.CartViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    var isLoggedIn by remember { mutableStateOf(true) }
    var userName by remember { mutableStateOf("Deal Hunter") }
    var userEmail by remember { mutableStateOf("hunter@dealapp.com") }
    var userPhone by remember { mutableStateOf("+1 234 567 8900") }
    var totalSavings by remember { mutableStateOf(1250.0) }
    var dealsFound by remember { mutableStateOf(47) }
    var membershipLevel by remember { mutableStateOf("Gold Saver") }
    
    var showEditProfile by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    
    val profileScale by animateFloatAsState(
        targetValue = if (showEditProfile) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "profileScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DealOrangePrimary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            item {
                // Top Profile Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                        containerColor = DealOrangePrimary
                ),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            ) {
                Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Profile Picture
                        Card(
                            modifier = Modifier.size(120.dp),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.2f)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "Profile Picture",
                                    tint = Color.White,
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // User Name
                        Text(
                            text = userName,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        
                        // User Email
                        Text(
                            text = userEmail,
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Membership Badge
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = DealPurpleAccent
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Membership",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = membershipLevel,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                // Stats Cards
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Total Savings Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = DealSuccess.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Savings,
                                contentDescription = "Total Savings",
                                tint = DealSuccess,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "$${totalSavings.toInt()}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = DealSuccess
                            )
                            Text(
                                text = "Total Saved",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    
                    // Deals Found Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = DealPurpleAccent.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocalOffer,
                                contentDescription = "Deals Found",
                                tint = DealPurpleAccent,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = dealsFound.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = DealPurpleAccent
                            )
                            Text(
                                text = "Deals Found",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                // Profile Options
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
            Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Account Settings",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                        // Edit Profile
                        ProfileMenuItem(
                            icon = Icons.Filled.Edit,
                            title = "Edit Profile",
                            subtitle = "Update your personal information",
                            iconColor = DealOrangePrimary,
                            onClick = { showEditProfile = true }
                        )
                        
                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = DealLightGray
                        )
                        
                        // Deal Preferences
                        ProfileMenuItem(
                            icon = Icons.Filled.Tune,
                            title = "Deal Preferences",
                            subtitle = "Customize your deal notifications",
                            iconColor = DealPurpleAccent,
                            onClick = { /* Handle deal preferences */ }
                        )
                        
                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = DealLightGray
                        )
                        
                        // Saved Deals
                        ProfileMenuItem(
                            icon = Icons.Filled.Bookmark,
                            title = "Saved Deals",
                            subtitle = "View your bookmarked deals",
                            iconColor = DealSuccess,
                            onClick = { /* Handle saved deals */ }
                        )
                        
                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = DealLightGray
                        )
                        
                        // Deal History
                        ProfileMenuItem(
                            icon = Icons.Filled.History,
                            title = "Deal History",
                            subtitle = "View your deal activity",
                            iconColor = DealInfo,
                            onClick = { /* Handle deal history */ }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                // App Settings
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "App Settings",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Notifications
                        ProfileMenuItem(
                            icon = Icons.Filled.Notifications,
                            title = "Notifications",
                            subtitle = "Manage deal alerts and updates",
                            iconColor = DealWarning,
                            onClick = { /* Handle notifications */ }
                        )
                        
                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = DealLightGray
                        )
                        
                        // Privacy & Security
                        ProfileMenuItem(
                            icon = Icons.Filled.Security,
                            title = "Privacy & Security",
                            subtitle = "Manage your account security",
                            iconColor = DealError,
                            onClick = { /* Handle privacy */ }
                        )
                        
                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = DealLightGray
                        )
                        
                        // Help & Support
                        ProfileMenuItem(
                        icon = Icons.Filled.Help,
                        title = "Help & Support",
                            subtitle = "Get help with DealHunter",
                            iconColor = DealInfo,
                            onClick = { /* Handle help */ }
                        )
                        
                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = DealLightGray
                        )
                        
                        // About
                        ProfileMenuItem(
                            icon = Icons.Filled.Info,
                            title = "About DealHunter",
                            subtitle = "App version and information",
                            iconColor = DealPurpleAccent,
                            onClick = { /* Handle about */ }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                // Logout Button
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                Button(
                        onClick = { showLogoutDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                        .height(56.dp)
                            .scale(profileScale),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                            containerColor = DealError
                    )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                                imageVector = Icons.Filled.Logout,
                                contentDescription = "Logout",
                        tint = Color.White,
                                modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                                text = "Sign Out",
                                fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
                
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
    
    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        isLoggedIn = false
                        navController.navigate("login") {
                            popUpTo("profile") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DealError
                    )
                ) {
                    Text("Sign Out", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("Cancel", color = DealOrangePrimary)
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = "Logout",
                        tint = DealError,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sign Out?",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            text = {
                Text(
                    text = "Are you sure you want to sign out of DealHunter? You'll miss out on exclusive deals and flash sales!",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
    
    // Edit Profile Dialog (placeholder)
    if (showEditProfile) {
        AlertDialog(
            onDismissRequest = { showEditProfile = false },
            confirmButton = {
                Button(
                    onClick = { showEditProfile = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DealOrangePrimary
                    )
                ) {
                    Text("Save Changes", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEditProfile = false }
                ) {
                    Text("Cancel", color = DealOrangePrimary)
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Profile",
                        tint = DealOrangePrimary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                Text(
                        text = "Edit Profile",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            },
            text = {
                Column {
                    Text(
                        text = "Update your profile information to get personalized deal recommendations.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            Spacer(modifier = Modifier.height(16.dp))
            
                    OutlinedTextField(
                        value = userName,
                        onValueChange = { userName = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DealOrangePrimary,
                            focusedLabelColor = DealOrangePrimary,
                            cursorColor = DealOrangePrimary
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    OutlinedTextField(
                        value = userEmail,
                        onValueChange = { userEmail = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DealOrangePrimary,
                            focusedLabelColor = DealOrangePrimary,
                            cursorColor = DealOrangePrimary
                        )
                    )
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
private fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
            modifier = Modifier.size(40.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                containerColor = iconColor.copy(alpha = 0.1f)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                    )
                }
            }
            
        Spacer(modifier = Modifier.width(16.dp))
            
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = "Navigate",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
    }
} 