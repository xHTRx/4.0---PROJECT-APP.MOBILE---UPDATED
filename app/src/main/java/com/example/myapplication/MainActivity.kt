package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.uiprojeto.Cabecalho
import com.example.myapplication.uiprojeto.Rodape
import com.example.myapplication.uiprojeto.TelaCadastroUsuario
import com.example.myapplication.uiprojeto.TelaCarteirinha
import com.example.myapplication.uiprojeto.TelaCronograma
import com.example.myapplication.uiprojeto.TelaHome
import com.example.myapplication.uiprojeto.TelaOfertas
import com.example.myapplication.uiprojeto.Telaqr

// =================================================================================
// 1. ACTIVITY PRINCIPAL
// =================================================================================

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppScreen()
            }
        }
    }
}

// =================================================================================
// 2. TELA PRINCIPAL E NAVEGAÇÃO
// =================================================================================

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AppScreen() {

    // --- Configuração da Navegação ---
    val navController = rememberNavController()

    val routeToTitleMap = mapOf(
        "home" to "Home",
        "carteirinha" to "Carteirinha",
        "ofertas" to "Benefícios e Ofertas",
        "qrcode" to "QR Code",
        "cadastroUsuario" to "Cadastro de Usuário",
        "cronograma" to "Agenda Mensal"
    )

    // --- Controle de Título Dinâmico ---
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentTitle = routeToTitleMap[currentRoute] ?: "Home"

    // --- Estrutura da Tela (Scaffold) ---
    Scaffold(
        topBar = {
            Box(modifier = Modifier.padding(top = 0.dp)) {
                Cabecalho(titulo = currentTitle, mostrarIconeDeRosto = true)
            }
        },
        bottomBar = {
            Box(modifier = Modifier.padding(bottom = 25.dp)) {
                Rodape(navController = navController)
            }
        }
    ) { innerPadding ->

        // --- Definição das Rotas (NavHost) ---
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                TelaHome(navController = navController)
            }
            composable("carteirinha") {
                TelaCarteirinha()
            }
            composable("ofertas") {
                TelaOfertas()
            }
            composable("qrcode") {
                Telaqr()
            }
            composable("cadastroUsuario") {
                TelaCadastroUsuario()
            }
            composable("cronograma") {
                TelaCronograma()
            }
        }
    }
}