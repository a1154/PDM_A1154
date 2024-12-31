package com.example.aalista_compras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aalista_compras.ui.theme.ListaDeComprasTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListaDeComprasTheme {
                val navController = rememberNavController()

                // Cria uma única instância do ViewModel compartilhado
                val shoppingListViewModel: ShoppingListViewModel = viewModel()

                // Configuração do NavHost
                NavHost(
                    navController = navController,
                    startDestination = getStartDestination() // Verifica o estado do login
                ) {
                    composable("login") {
                        LoginView(navController = navController)
                    }
                    composable("register") {
                        RegisterView(navController = navController)
                    }
                    composable("shopping_list") {
                        ShoppingListView(
                            navController = navController,
                            viewModel = shoppingListViewModel // Passa o ViewModel compartilhado
                        )
                    }
                    composable("add_item") {
                        AddItemView(
                            navController = navController,
                            viewModel = shoppingListViewModel // Passa o mesmo ViewModel
                        )
                    }
                    composable(
                        "edit_articles/{itemId}",
                        arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val itemId = backStackEntry.arguments?.getString("itemId")
                        if (itemId != null) {
                            EditArticlesView(
                                navController = navController,
                                itemId = itemId,
                                viewModel = shoppingListViewModel // Passa o ViewModel compartilhado
                            )
                        }
                    }
                }
            }
        }
    }

    // Verifica se o utilizador está logado
    private fun getStartDestination(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return if (currentUser != null) "shopping_list" else "login"
    }
}


