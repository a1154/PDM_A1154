package com.example.novacalculadora.ui.theme
fun formatDisplayText(number: Double): String {
    // Verifica se o número é um inteiro
    val formatted = if (number == number.toInt().toDouble()) {
        number.toInt().toString() // Remove .0 se o resultado for um número inteiro
    } else {
        "%.13f".format(number) // Formata até 13 casas decimais
    }

    // Se o resultado formatado for maior que 14 caracteres, converte para notação científica
    return if (formatted.length > 14) {
        "%.6e".format(number) // Exibe em notação científica com 6 dígitos significativos
    } else {
        formatted.take(14) // Trunca para 14 caracteres
    }
}