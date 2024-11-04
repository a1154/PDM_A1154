package com.example.novacalculadora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorScreen() {
    val displayText = remember { mutableStateOf("0") }
    val currentNumber = remember { mutableStateOf("") }
    val previousNumber = remember { mutableStateOf("") }
    val operation = remember { mutableStateOf<String?>(null) }
    val isResultDisplayed = remember { mutableStateOf(false) }
    val calculatorOperations = CalculatorOperations()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF4E3)) // Fundo com a cor  pérola
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Visor com profundidade
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(4.dp), // Espaços para o efeito de sombra
            color = Color.Gray,
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 8.dp // Define a profundidade para o display
        ) {
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = displayText.value,
                    fontSize = 36.sp,
                    color = Color.White,
                    textAlign = TextAlign.End
                )
            }
        }
        // Texto com Nome e número do aluno
        Text(
            text = "Fernando Moura - 1154 LESI",
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Botões Numéricos e de Operação
        val buttons = listOf(
            listOf("7", "8", "9", "+"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "*"),
            listOf(".", "0", "+/-", "/"),
            listOf("C", "%", "=", "√")
        )

        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (buttonText in row) {
                    Surface(
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp),
                        color = if (buttonText == "C") Color(0xFFFFA500) else Color(0xFF1E90FF), // Cor laranja para "C", azul para os outros
                        shape = MaterialTheme.shapes.small, // Define o formato dos cantos
                        shadowElevation = 4.dp // Relevo para destacar os botões
                    ) {
                        Button(
                            onClick = {
                                handleButtonAction(
                                    input = buttonText,
                                    currentNumber = currentNumber,
                                    previousNumber = previousNumber,
                                    operation = operation,
                                    displayText = displayText,
                                    calculatorOperations = calculatorOperations,
                                    isResultDisplayed = isResultDisplayed
                                )
                            },
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp),
                            colors = if (buttonText == "C") ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFA500) // Cor laranja para o botão C
                            ) else ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1E90FF) // Azul para os outros botões
                            )
                        ) {
                            Text(buttonText, fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    }
}