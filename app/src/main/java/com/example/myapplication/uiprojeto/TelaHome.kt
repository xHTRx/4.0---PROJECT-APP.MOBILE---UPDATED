package com.example.myapplication.uiprojeto

import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R

// =================================================================================
// 1. TELA PRINCIPAL
// =================================================================================

@Composable
fun TelaHome(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SecaoCentralH(navController = navController)
    }
}

// =================================================================================
// 2. SEÇÃO CENTRAL (ORQUESTRADOR)
// =================================================================================

@Composable
fun SecaoCentralH(modifier: Modifier = Modifier, navController: NavController) {

    // Nota: O LocalContext.current não é mais necessário aqui, pois é usado internamente em AcoesUtilidadeRow.

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        AppLogo()
        Spacer(modifier = Modifier.height(100.dp))

        // --- Principais Ações de Navegação ---
        AcoesPrincipaisRow(navController = navController)
        Spacer(modifier = Modifier.height(9.dp))
        AcoesSecundariasRow(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))

        // --- Ações de Utilidade (Ações Nativas do Android) ---
        AcoesUtilidadeRow(navController = navController) // NavController é passado
    }
}

// =================================================================================
// 3. SUB-SEÇÕES (UI E AÇÕES)
// =================================================================================

@Composable
fun AppLogo() {
    Image(
        painter = painterResource(id = R.drawable.logoup),
        contentDescription = "Logo da aplicação",
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 350.dp)
    )
}

@Composable
fun AcoesPrincipaisRow(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardSecao(
            texto = "Carteirinha",
            modifier = Modifier.weight(1f).height(100.dp),
            cor = Color(0xFFD80E0E),
            imagemResId = R.drawable.celularcard,
            onClick = { navController.navigate("carteirinha") }
        )
        Spacer(modifier = Modifier.width(12.dp))
        CardSecao(
            texto = "Qr Code",
            modifier = Modifier.weight(1f).height(100.dp),
            cor = Color(0xFF08259D),
            imagemResId = R.drawable.qrcode,
            onClick = { navController.navigate("qrcode") }
        )
    }
}

@Composable
fun AcoesSecundariasRow(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardSecao(
            texto = "Cronograma",
            modifier = Modifier.weight(1f).height(100.dp),
            cor = Color(0xFFEAB505),
            imagemResId = R.drawable.identidade,
            onClick = { navController.navigate("cronograma") }
        )
        Spacer(modifier = Modifier.width(12.dp))
        CardSecao(
            texto = "Bonus & Ofertas",
            modifier = Modifier.weight(1f).height(100.dp),
            cor = Color(0xFF1D9E99),
            imagemResId = R.drawable.presente,
            onClick = { navController.navigate("ofertas") }
        )
    }
}

@Composable
fun AcoesUtilidadeRow(navController: NavController) { // Agora aceita NavController
    val context = LocalContext.current // Context obtido aqui, onde é usado

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardSecao(
            texto = "Info. Usuário",
            modifier = Modifier.weight(1f).height(100.dp),
            cor = Color(0xFF7C33A8),
            imagemResId = R.drawable.user,
            onClick = {
                // CORRIGIDO: Usa o NavController para navegação
                navController.navigate("cadastroUsuario")
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        CardSecao(
            texto = "Camera",
            modifier = Modifier.weight(1f).height(100.dp),
            cor = Color(0xFFFF4500),
            imagemResId = R.drawable.camera,
            onClick = {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                context.startActivity(intent) // Usa o Context para ações do Android
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        CardSecao(
            texto = "Notificações",
            modifier = Modifier.weight(1f).height(100.dp),
            cor = Color(0xFF16703C),
            imagemResId = R.drawable.sino,
            onClick = {
                Toast.makeText(context, "Você não possui notificações!", Toast.LENGTH_SHORT).show() // Usa o Context para ações do Android
            }
        )
    }
}