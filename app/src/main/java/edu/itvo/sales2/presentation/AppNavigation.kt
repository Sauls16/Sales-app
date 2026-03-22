package edu.itvo.sales2.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.itvo.sales2.presentation.customer.create.CreateCustomerScreen
import edu.itvo.sales2.presentation.customer.list.ListCustomerScreen
import edu.itvo.sales2.presentation.product.create.CreateProductScreen
import edu.itvo.sales2.presentation.product.list.ListProductScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Esto nos permite saber en qué pantalla estamos actualmente
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Solo mostramos la barra inferior en las listas, no en los formularios
            if (currentRoute == "product_list" || currentRoute == "customer_list") {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Productos") },
                        label = { Text("Productos") },
                        selected = currentRoute == "product_list",
                        onClick = {
                            navController.navigate("product_list") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Clientes") },
                        label = { Text("Clientes") },
                        selected = currentRoute == "customer_list",
                        onClick = {
                            navController.navigate("customer_list") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "product_list",
            modifier = Modifier.padding(paddingValues)
        ) {
            // --- SECCIÓN PRODUCT ---
            composable("product_list") {
                ListProductScreen(
                    onAddProduct = {
                        navController.navigate("create_product")
                    }
                )
            }
            composable("create_product") {
                CreateProductScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // --- SECCIÓN CUSTOMER ---
            composable("customer_list") {
                ListCustomerScreen(
                    onAddCustomer = { navController.navigate("create_customer") },
                    onGoToProducts = { navController.navigate("product_list") }
                )
            }
            composable("create_customer") {
                CreateCustomerScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}