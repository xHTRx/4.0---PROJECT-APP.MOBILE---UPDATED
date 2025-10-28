package com.example.myapplication.uiprojeto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import kotlinx.coroutines.delay

// =================================================================================
// 1. COMPOSABLE PRINCIPAL (TELA)
// =================================================================================

@Composable
fun Telaqr(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(), // Usa o Modifier passado (que incluirá o padding do Scaffold)
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SecaoCentralqr(modifier = Modifier.weight(1f))
    }
}

// =================================================================================
// 2. SEÇÃO CENTRAL (TÍTULO E LAYOUT)
// =================================================================================

@Composable
fun SecaoCentralqr(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Escaneie o QR Code!!",
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(40.dp))
        DynamicQrCodeImage()
    }
}

// =================================================================================
// 3. QR CODE DINÂMICO (LÓGICA E ESTADO)
// =================================================================================

@Composable
fun DynamicQrCodeImage() {
    // --- Dados ---
    val qrCodeImages = listOf(
        R.drawable.myqrcode,
        R.drawable.myqrcode2,
        R.drawable.myqrcode3,
    )

    // --- Estado e Lógica Temporal ---
    var currentImageIndex by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(10000L) // Alterna a cada 10 segundos
            currentImageIndex = (currentImageIndex + 1) % qrCodeImages.size
        }
    }

    // --- UI do QR Code ---
    Box(
        modifier = Modifier
            .size(350.dp)
            .border(BorderStroke(8.dp, Color.Red), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = qrCodeImages[currentImageIndex]),
            contentDescription = "QR Code Dinâmico",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )
    }
}