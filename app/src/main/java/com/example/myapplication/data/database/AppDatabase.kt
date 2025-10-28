package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.database.dao.CronogramaDAO
import com.example.myapplication.data.database.dao.UsuarioDAO
import com.example.myapplication.data.database.entities.Cronograma
import com.example.myapplication.data.database.entities.Usuario

// =================================================================================
// 1. DEFINIÇÃO DO BANCO DE DADOS ROOM
// =================================================================================

@Database(
    entities = [Usuario::class, Cronograma::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // --- Métodos de Acesso aos DAOs ---

    abstract fun usuarioDAO(): UsuarioDAO
    abstract fun cronogramaDAO(): CronogramaDAO

    // =================================================================================
    // 2. PADRÃO SINGLETON (GARANTE UMA ÚNICA INSTÂNCIA)
    // =================================================================================

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Retorna a instância única do banco de dados, criando-a se necessário.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cateirinha_database"
                )
                    // Estratégia de migração destrutiva para prototipagem/desenvolvimento
                    .fallbackToDestructiveMigration(false)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}