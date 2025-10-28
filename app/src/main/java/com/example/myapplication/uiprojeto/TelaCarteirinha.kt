package com.example.myapplication.uiprojeto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

// =================================================================================
// 1. TELA PRINCIPAL
// =================================================================================

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TelaCarteirinha(modifier: Modifier = Modifier) {

    // --- Dados ---
    val images = listOf(
        R.drawable.carteira,
        R.drawable.versocarteira
    )

    // --- Estrutura da Tela ---
    Column(
        modifier = modifier.fillMaxSize(), // Usa o modifier que contém o padding do Scaffold principal
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CarteirinhaPager(images = images)
    }
}

// =================================================================================
// 2. COMPONENTE DO PAGER (LÓGICA E APRESENTAÇÃO DO CARD)
// =================================================================================

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarteirinhaPager(images: List<Int>) {

    val pagerState = rememberPagerState(initialPage = 0)

    Surface(
        modifier = Modifier
            .fillMaxWidth(0.88f)
            .heightIn(max = 550.dp)
            .padding(16.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 8.dp
    ) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            // --- UI da Imagem (Frente/Verso) ---
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = if (page == 0) "Frente da Carteirinha Universitária" else "Verso da Carteirinha Universitária",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// =================================================================================
// 3. PREVIEW
// =================================================================================

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalPagerApi::class)
fun TelaCarteirinhaPreview() {
    TelaCarteirinha()
}