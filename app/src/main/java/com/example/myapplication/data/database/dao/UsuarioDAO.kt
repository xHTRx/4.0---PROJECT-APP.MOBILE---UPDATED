package com.example.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.database.entities.Usuario
import kotlinx.coroutines.flow.Flow

// =================================================================================
// 1. DATA ACCESS OBJECT (DAO): UsuarioDAO
// =================================================================================

@Dao
interface UsuarioDAO {

    // --- Operações de Escrita (Suspend Functions) ---

    /**
     * Insere um novo usuário na tabela.
     */
    @Insert
    suspend fun inserir(usuario: Usuario)

    /**
     * Atualiza um usuário existente na tabela.
     */
    @Update
    suspend fun atualizar(usuario: Usuario)

    /**
     * Deleta um usuário da tabela.
     */
    @Delete
    suspend fun deletar(usuario: Usuario)

    // --- Operações de Leitura (Suspend Functions) ---

    /**
     * Busca um usuário específico pelo ID.
     */
    @Query("SELECT * FROM tabela_usuarios WHERE id = :idUsuario")
    suspend fun buscarPorId(idUsuario: Int): Usuario?

    /**
     * Busca o único (ou primeiro) usuário na tabela de forma síncrona.
     */
    @Query("SELECT * FROM tabela_usuarios LIMIT 1")
    suspend fun buscarUsuarioUnico(): Usuario?

    /**
     * Busca todos os usuários na tabela (útil para debug ou admins).
     */
    @Query("SELECT * FROM tabela_usuarios ORDER BY id ASC")
    suspend fun buscarTodos(): List<Usuario>

    // --- Operações de Leitura (Flow Reativo para MVVM) ---

    /**
     * Busca o único (ou primeiro) usuário na tabela de forma reativa.
     * Retorna um Flow que emite o usuário sempre que os dados mudam.
     */
    @Query("SELECT * FROM tabela_usuarios LIMIT 1")
    fun buscarUsuarioUnicoFlow(): Flow<Usuario?>
}