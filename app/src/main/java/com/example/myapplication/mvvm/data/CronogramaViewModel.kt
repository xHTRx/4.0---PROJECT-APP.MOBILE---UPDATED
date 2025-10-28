package com.example.myapplication.mvvm.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.entities.Cronograma
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// =================================================================================
// 1. DATA CLASS: CronogramaUiState (Estado da Tela)
// =================================================================================

data class CronogramaUiState(
    val listaCronograma: List<Cronograma> = emptyList(),
    val isLoading: Boolean = true,
    val showSnackbar: Boolean = false,
    val snackbarMessage: String = "",
)

// =================================================================================
// 2. VIEWMODEL: CronogramaViewModel (Lógica de Negócios)
// =================================================================================

class CronogramaViewModel(private val repository: CronogramaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CronogramaUiState())
    val uiState: StateFlow<CronogramaUiState> = _uiState.asStateFlow()

    init {
        // Inicia o carregamento da lista ao criar o ViewModel
        carregarCronograma()
    }

    /**
     * Busca ou cria a lista completa do cronograma.
     */
    fun carregarCronograma() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val lista = repository.buscarOuCriarCronograma()
                _uiState.update {
                    it.copy(
                        listaCronograma = lista,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // Em caso de erro, encerra o loading
                _uiState.update { it.copy(isLoading = false) }
                // Opcional: Adicionar lógica de Snackbar para informar o erro ao usuário
            }
        }
    }

    /**
     * Salva ou limpa um item do cronograma.
     */
    fun onSave(itemEditado: Cronograma) {
        viewModelScope.launch {
            try {
                repository.atualizar(itemEditado)

                val mensagem = if (itemEditado.nome.isNullOrBlank())
                    "Dia ${itemEditado.diaDoMes} limpo!"
                else
                    "Dia ${itemEditado.diaDoMes} atualizado!"

                // Recarrega a lista para refletir a mudança no banco de dados
                carregarCronograma()

                // Exibe o snackbar
                _uiState.update {
                    it.copy(
                        showSnackbar = true,
                        snackbarMessage = mensagem
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        showSnackbar = true,
                        snackbarMessage = "Erro ao salvar: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Evento para fechar o snackbar após a exibição.
     */
    fun onSnackbarDismiss() = _uiState.update { it.copy(showSnackbar = false) }

}

// =================================================================================
// 3. FACTORY: CronogramaViewModelFactory (Injeção de Dependência)
// =================================================================================

class CronogramaViewModelFactory(private val repository: CronogramaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CronogramaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CronogramaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}