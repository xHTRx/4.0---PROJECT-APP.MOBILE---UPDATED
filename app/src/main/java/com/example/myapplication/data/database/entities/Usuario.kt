package com.example.myapplication.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// =================================================================================
// 1. ENTIDADE ROOM: Usuario
// =================================================================================

// Define o nome da tabela no banco de dados.
@Entity(tableName = "tabela_usuarios")
data class Usuario(

    // Define 'id' como chave primária e permite que o Room gere o valor automaticamente.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // --- Campos de Dados ---
    val nome: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val telefone: String? // Campo opcional
)