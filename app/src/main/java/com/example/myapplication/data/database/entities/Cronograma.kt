package com.example.myapplication.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// =================================================================================
// 1. ENTIDADE ROOM: Cronograma
// =================================================================================

// Define o nome da tabela no banco de dados.
@Entity(tableName = "tabela_cronograma")
data class Cronograma(

    // Define 'id' como chave primária e permite que o Room gere o valor automaticamente.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // --- Campos de Dados ---
    val diaDoMes: Int,
    val nome: String?, // Nome do evento/compromisso
    val horario: String?, // Horário do compromisso
    val descricao: String? // Descrição ou detalhes adicionais
)