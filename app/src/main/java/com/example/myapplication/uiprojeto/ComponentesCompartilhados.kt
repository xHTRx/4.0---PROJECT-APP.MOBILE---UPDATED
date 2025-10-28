package com.example.myapplication.uiprojeto

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination // MANTIDO: Necessário para a função popUpTo

// =================================================================================
// 1. CABEÇALHO (TOPO DA TELA)
// =================================================================================

@Composable
fun Cabecalho(titulo: String, mostrarIconeDeRosto: Boolean) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        modifier = Modifier
            .height(110.dp)
            .fillMaxWidth(),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(26.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.iconecabecalho),
                contentDescription = "Imagem de Perfil",
                modifier = Modifier.size(50.dp)
            )
            Column {
                Text(
                    titulo,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (mostrarIconeDeRosto) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Ícone de Perfil",
                    modifier = Modifier.size(50.dp),
                    tint = Color.White
                )
            }
        }
    }
}

// =================================================================================
// 2. RODAPÉ (BARRA DE NAVEGAÇÃO INFERIOR)
// =================================================================================

/**
 * Função auxiliar para padronizar a navegação no Rodapé.
 * Garante que a pilha de navegação seja limpa até o início e que apenas uma instância exista.
 */
private fun NavController.navigateAndClearStack(route: String) {
    this.navigate(route) {
        // Limpa o estado da pilha (back stack) até o destino inicial
        popUpTo(this@navigateAndClearStack.graph.findStartDestination().id) {
            saveState = true // Salva o estado do destino anterior, se houver
        }
        launchSingleTop = true // Evita múltiplas cópias do mesmo destino
        restoreState = true // Restaura o estado salvo se o destino estiver no back stack
    }
}

@Composable
fun Rodape(navController: NavController) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround, // Usando SpaceAround para melhor espaçamento
                modifier = Modifier.fillMaxWidth() // Garantindo que o espaçamento cubra a largura
            ) {
                // Navegação para Home
                Image(
                    painter = painterResource(id = R.drawable.casalogo),
                    contentDescription = "Ícone de casa",
                    modifier = Modifier
                        .size(80.dp) // Reduzido o tamanho para 80.dp para melhor uniformidade
                        .clickable { navController.navigateAndClearStack("home") }
                )
                // Navegação para Carteirinha
                Image(
                    painter = painterResource(id = R.drawable.celularicone),
                    contentDescription = "Ícone de celular",
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { navController.navigateAndClearStack("carteirinha") }
                )
                // Navegação para Ofertas
                Image(
                    painter = painterResource(id = R.drawable.ofertasicone),
                    contentDescription = "Ícone de ofertas",
                    modifier = Modifier
                        .size(90.dp) // Mantido maior se necessário para o design
                        .clickable { navController.navigateAndClearStack("ofertas") }
                )
                // Navegação para QR Code
                Image(
                    painter = painterResource(id = R.drawable.qrcodeicone),
                    contentDescription = "Ícone de QR Code",
                    modifier = Modifier
                        .size(70.dp) // Reduzido o tamanho
                        .clickable { navController.navigateAndClearStack("qrcode") }
                )
            }
        }
    }
}

// =================================================================================
// 3. CARD DE SEÇÃO REUTILIZÁVEL (USADO NA TELA HOME)
// =================================================================================

@Composable
fun CardSecao(
    texto: String,
    modifier: Modifier = Modifier,
    cor: Color,
    imagemResId: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = cor),
        shape = RoundedCornerShape(8.dp), // Adicionando shape para um visual mais moderno e padrão
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp), // Aumentando o padding interno
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly // Usando SpaceEvenly para distribuir o conteúdo
        ) {
            Image(
                painter = painterResource(id = imagemResId),
                contentDescription = null, // Content description pode ser null se o texto do card já for suficiente
                modifier = Modifier.size(45.dp) // Tamanho ajustado para caber melhor
            )
            // Removido o Spacer
            Text(
                text = texto,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                // Centralizando o texto
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}