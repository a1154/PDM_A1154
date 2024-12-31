package com.example.aalista_compras

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AddItemView(navController: NavController, viewModel: ShoppingListViewModel = viewModel()) {
    var newItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = newItem,
            onValueChange = { newItem = it },
            label = { Text("Nova Lista") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (newItem.isNotBlank()) {
                    viewModel.addItem(newItem)
                    navController.navigate("shopping_list")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar")
        }
    }
}
