package com.example.myapplication.mvvm.data

import com.example.myapplication.data.database.dao.UsuarioDAO
import com.example.myapplication.data.database.entities.Usuario
import kotlinx.coroutines.flow.Flow

// =================================================================================
// 1. REPOSITORY: UsuarioRepository (Acesso e Abstração de Dados)
// =================================================================================

/**
 * Repository responsável por abstrair a fonte de dados (Room DAO) para o ViewModel.
 * Focado em operações CRUD para a entidade Usuario.
 */
class UsuarioRepository(private val usuarioDAO: UsuarioDAO) {

    // --- Operações de Leitura (Reativas) ---

    /**
     * Retorna um Flow reativo contendo o único usuário cadastrado (ou null).
     */
    fun buscarUsuarioUnicoFlow(): Flow<Usuario?> {
        return usuarioDAO.buscarUsuarioUnicoFlow()
    }

    // --- Operações de Escrita (Suspensas) ---

    /**
     * Insere um novo usuário.
     */
    suspend fun inserir(usuario: Usuario) {
        usuarioDAO.inserir(usuario)
    }

    /**
     * Atualiza um usuário existente.
     */
    suspend fun atualizar(usuario: Usuario) {
        usuarioDAO.atualizar(usuario)
    }

    /**
     * Deleta um usuário existente.
     */
    suspend fun deletar(usuario: Usuario) {
        usuarioDAO.deletar(usuario)
    }

}