package com.example.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.database.entities.Cronograma

// =================================================================================
// 1. DATA ACCESS OBJECT (DAO): CronogramaDAO
// =================================================================================

@Dao
interface CronogramaDAO {

    // --- Operações de Leitura (Suspend Functions) ---

    /**
     * Busca todos os itens de cronograma.
     * Ordena pelo dia do mês para garantir a ordem correta na lista.
     */
    @Query("SELECT * FROM tabela_cronograma ORDER BY diaDoMes ASC")
    suspend fun getAll(): List<Cronograma>

    // --- Operações de Escrita (Suspend Functions) ---

    /**
     * Insere uma única entrada de cronograma (usado para popular o banco inicialmente).
     */
    @Insert
    suspend fun insert(item: Cronograma)

    /**
     * Insere múltiplos itens de cronograma de uma só vez (usado para popular os 30 dias).
     */
    @Insert
    suspend fun insertAll(items: List<Cronograma>)

    /**
     * Atualiza um item de cronograma existente.
     * Será usado para salvar as alterações de nome, horário e descrição de um dia.
     */
    @Update
    suspend fun update(item: Cronograma)

    /**
     * Deleta um item de cronograma (raramente usado para o cronograma de 30 dias, mas mantido).
     */
    @Delete
    suspend fun delete(item: Cronograma)
}