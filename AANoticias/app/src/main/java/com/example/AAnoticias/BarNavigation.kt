package com.example.AAnoticias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar() {
    TopAppBar(
        modifier = Modifier.height(60.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Gray
        ),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 14.dp), // Ajusta a posição vertical para 4 dp abaixo
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "NOTÍCIAS DO MUNDO",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
            }
        }

    )
}

@Composable
fun CustomBottomAppBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.height(48.dp),
        containerColor = Color.Gray
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = { /* Ação para o segundo botão */ }) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Favoritos",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = { /* Ação para o terceiro botão */ }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Configurações",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}
