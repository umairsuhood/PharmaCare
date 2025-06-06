package com.example.jimstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jimstore.screens.HomeScreen
import com.example.jimstore.screens.LoginScreen
import com.example.jimstore.screens.MasterView
import com.example.jimstore.screens.SignUpScreen
import com.example.jimstore.screens.ProductScreen
import com.example.jimstore.screens.ProfileScreen
import com.example.jimstore.screens.CartScreen
import com.example.jimstore.screens.CheckoutScreen
import com.example.jimstore.model.Product
import com.example.jimstore.viewmodel.CartViewModel

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val HOME = "home"
    const val PRODUCTS = "products"
    const val CART = "cart"
    const val PROFILE = "profile"
    const val CHECKOUT = "checkout"
    const val MASTER = "master/{nameRes}/{priceRes}/{imageRes}"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onSignUpClick = {
                    navController.navigate(Routes.SIGNUP)
                },
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.SIGNUP) {
            SignUpScreen(
                onLoginClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onSignUpSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
        composable(Routes.PRODUCTS) {
            ProductScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
        composable(Routes.CART) {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
        composable(Routes.CHECKOUT) {
            CheckoutScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
        composable(
            route = Routes.MASTER,
            arguments = listOf(
                navArgument("nameRes") { type = androidx.navigation.NavType.IntType },
                navArgument("priceRes") { type = androidx.navigation.NavType.IntType },
                navArgument("imageRes") { type = androidx.navigation.NavType.IntType }
            )
        ) { backStackEntry ->
            val nameRes = backStackEntry.arguments?.getInt("nameRes") ?: return@composable
            val priceRes = backStackEntry.arguments?.getInt("priceRes") ?: return@composable
            val imageRes = backStackEntry.arguments?.getInt("imageRes") ?: return@composable
            
            // Find the product to get the complete data including ID and price
            val product = com.example.jimstore.data.ProductDataSource.allProducts.find { 
                it.nameRes == nameRes && it.priceRes == priceRes && it.imageRes == imageRes 
            } ?: Product(
                    nameRes = nameRes,
                    priceRes = priceRes,
                    imageRes = imageRes
            )
            
            MasterView(
                product = product,
                cartViewModel = cartViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onBuy = {
                    // Handle buy action - could navigate to checkout
                    cartViewModel.addToCart(product)
                    navController.navigate(Routes.CHECKOUT)
                },
                navController = navController
            )
        }
    }
} 