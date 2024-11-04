package com.example.novacalculadora

import androidx.compose.runtime.MutableState
import com.example.novacalculadora.ui.theme.formatDisplayText
import kotlin.math.sqrt

fun handleButtonAction(
    input: String,
    currentNumber: MutableState<String>,
    previousNumber: MutableState<String>,
    operation: MutableState<String?>,
    displayText: MutableState<String>,
    calculatorOperations: CalculatorOperations,
    isResultDisplayed: MutableState<Boolean> // Nova flag para controlar o estado do resultado
) {
    when (input) {
        in "0".."9" -> {
            // Adiciona o número atual
            // Limpa o display se um resultado foi exibido
            if (currentNumber.value.length < 14) {
                if (isResultDisplayed.value) {
                    currentNumber.value = ""
                    isResultDisplayed.value = false
                }

                currentNumber.value += input
                displayText.value = currentNumber.value
            }
        }

        "." -> {
            // Adiciona ponto decimal se não existir e o comprimento for menor que 14
            if (!currentNumber.value.contains(".") && currentNumber.value.length < 14) {
                currentNumber.value += "."
                displayText.value = currentNumber.value
            }
        }
        "+", "-", "*", "/" -> {
            // Armazena o número atual e a operação
            previousNumber.value = currentNumber.value
            currentNumber.value = ""
            operation.value = input
            isResultDisplayed.value = false
        }
        "%" -> {
            // Calcula percentagem do número atual
            val number = currentNumber.value.toDoubleOrNull() ?: 0.0
            currentNumber.value = (number / 100).toString()
            displayText.value = currentNumber.value
        }
        "√" -> {
            // Calcula a raiz quadrada do número atual
            val number = currentNumber.value.toDoubleOrNull() ?: 0.0
            val result = sqrt(number)
            // Verifica se o resultado é inteiro e formata sem o .0, se for
            // Aplica a formatação para garantir o limite de 14 caracteres e arredondamento
            displayText.value = formatDisplayText(result)
            currentNumber.value = displayText.value
        }
        "+/-" -> {
            // Alterna o sinal do número atual para negativo ou positivo
            if (currentNumber.value.isNotEmpty()) {
                currentNumber.value = if (currentNumber.value.startsWith("-")) {
                    currentNumber.value.removePrefix("-")
                } else {
                    "-${currentNumber.value}"
                }
                displayText.value = currentNumber.value
            }
        }

        "=" -> {
            // Realiza o cálculo com os valores e a operação
            val result = when (operation.value) {
                "+" -> calculatorOperations.add(
                    previousNumber.value.toDouble(),
                    currentNumber.value.toDouble()
                )
                "-" -> calculatorOperations.subtract(
                    previousNumber.value.toDouble(),
                    currentNumber.value.toDouble()
                )
                "*" -> calculatorOperations.multiply(
                    previousNumber.value.toDouble(),
                    currentNumber.value.toDouble()
                )
                "/" -> calculatorOperations.divide(
                    previousNumber.value.toDouble(),
                    currentNumber.value.toDouble()
                )
                else -> currentNumber.value.toDouble()
            }

            // Formatar o resultado para exibir sem .0 quando for um inteiro
            //displayText.value = if (result == result.toInt().toDouble()) {
                //result.toInt().toString()  // Remove o .0 se for inteiro
            //} else {
                //result.toString()  // Mantém o decimal se não for inteiro
            //}
            displayText.value = formatDisplayText(result)


            currentNumber.value = displayText.value
            previousNumber.value = ""
            operation.value = null
            isResultDisplayed.value = true // Define a flag para indicar que o resultado foi exibido
        }
        "C" -> {
            // Limpa todos os valores
            currentNumber.value = ""
            previousNumber.value = ""
            operation.value = null
            displayText.value = "0" // Garante que o display mostra o 0
            isResultDisplayed.value = false
        }
    }
}



