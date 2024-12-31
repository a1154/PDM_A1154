package com.example.aalista_compras

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun EditArticlesView(
    navController: NavController,
    itemId: String,
    viewModel: ShoppingListViewModel = viewModel()
) {
    val item = viewModel.shoppingList.value.find { it.id == itemId }
    var newArticleName by remember { mutableStateOf("") }
    var newArticleQuantity by remember { mutableStateOf("1") }

    if (item != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Editar Artigos: ${item.name}", style = MaterialTheme.typography.headlineSmall)

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(item.articles) { article ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${article.name} - ${article.quantity}")
                        Checkbox(
                            checked = article.isChecked,
                            onCheckedChange = { isChecked ->
                                viewModel.updateArticleCheckState(item.id, article.name, isChecked)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = newArticleName,
                onValueChange = { newArticleName = it },
                label = { Text("Nome do Artigo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = newArticleQuantity,
                onValueChange = { newArticleQuantity = it },
                label = { Text("Quantidade") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (newArticleName.isNotBlank() && newArticleQuantity.toIntOrNull() != null) {
                        viewModel.addArticleToItem(
                            itemId,
                            Article(newArticleName, newArticleQuantity.toInt())
                        )
                        newArticleName = ""
                        newArticleQuantity = "1"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar Artigo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("shopping_list") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Gravar e Sair")
            }
        }
    } else {
        Text("Item não encontrado!", style = MaterialTheme.typography.bodyLarge)
    }
}


