package com.example.aalista_compras

data class Article(
    val name: String = "",        // Nome do artigo
    val quantity: Int = 0,        // Quantidade
    var isChecked: Boolean = false // Estado da checkbox
)
