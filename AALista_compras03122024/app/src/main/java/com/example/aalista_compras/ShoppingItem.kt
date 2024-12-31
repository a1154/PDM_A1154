package com.example.aalista_compras

data class ShoppingItem(
    val id: String = "", // ID gerado pelo Firestore
    val name: String = "", // Nome da lista
    val date: String = "", // Data da criação
    val articles: List<Article> = listOf() // Lista de artigos
)