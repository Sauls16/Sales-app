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
import androidx.navigation.navArgument
import edu.itvo.sales2.presentation.customer.create.CreateCustomerScreen
import edu.itvo.sales2.presentation.customer.list.ListCustomerScreen
import edu.itvo.sales2.presentation.product.create.CreateProductScreen
import edu.itvo.sales2.presentation.product.list.ListProductScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
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
                    onAddProduct = { code ->
                        val route = if (code != null) "create_product?productCode=$code" else "create_product"
                        navController.navigate(route)
                    }
                )
            }

            composable(
                route = "create_product?productCode={productCode}",
                arguments = listOf(navArgument("productCode") { nullable = true })
            ) { backStackEntry ->
                val code = backStackEntry.arguments?.getString("productCode")

                CreateProductScreen(
                    productCode = code,
                    onNavigateBack = { navController.popBackStack() }
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
                    // Permitimos que la función reciba el código para editar
                    onAddCustomer = { code ->
                        val route = if (code != null) "create_customer?code=$code" else "create_customer"
                        navController.navigate(route)
                    },
                    onGoToProducts = { navController.navigate("product_list") }
                )
            }
            composable(
                route = "create_customer?code={code}",
                arguments = listOf(navArgument("code") { nullable = true })
            ) { backStackEntry ->
                val code = backStackEntry.arguments?.getString("code")
                CreateCustomerScreen(
                    customerCode = code,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}