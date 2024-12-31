package com.example.aalista_compras

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListView(navController: NavController, viewModel: ShoppingListViewModel = viewModel()) {
    val shoppingList = viewModel.shoppingList.collectAsState()
    val listState = rememberLazyListState() // Estado da rolagem para a lista principal
    val coroutineScope = rememberCoroutineScope() // Para executar ações de rolagem

    // Estado para mostrar o diálogo de confirmação
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Compras") },

                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_item") },
                modifier = Modifier.size(100.dp), // Botão maior
                containerColor = Color(0xFF00ACC1), // Cor azul claro personalizada
            ) {
                Text(
                    text = "+",
                    fontSize = 40.sp,             // Texto grande
                    fontWeight = FontWeight.Bold, // Texto em negrito
                    color = Color.White           // Texto branco
                )
            }

        }
    ) { padding ->
        LazyColumn(
            state = listState, // Vincula o estado de roller
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(shoppingList.value) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("edit_articles/${item.id}") },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nome: ${item.name}")
                        Text("Data: ${item.date}")
                        Text("Artigos: ${item.articles.size}")
                    }
                }
            }
        }
    }

    // Roller automática para o final da lista quando um item é adicionado
    LaunchedEffect(shoppingList.value.size) {
        if (shoppingList.value.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(shoppingList.value.lastIndex)
            }
        }
    }

    // Diálogo de confirmação de logout
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Sair da Conta") },
            text = { Text("Tem a certeza de que deseja sair da conta?") },
            confirmButton = {
                TextButton(onClick = {
                    FirebaseAuth.getInstance().signOut() // Desloga o utilizador
                    navController.navigate("login") {
                        popUpTo("shopping_list") { inclusive = true }
                    }
                    showLogoutDialog = false
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Não")
                }
            }
        )
    }
}



