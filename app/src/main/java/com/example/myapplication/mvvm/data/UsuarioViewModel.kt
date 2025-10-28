package com.example.myapplication.mvvm.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.entities.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// =================================================================================
// 1. DATA CLASS: UsuarioUiState (Estado da Tela)
// =================================================================================

data class UsuarioUiState(
    val usuarioPrincipal: Usuario? = null,
    val isLoading: Boolean = true,
    val nome: String = "",
    val email: String = "",
    val senha: String = "",
    val cpf: String = "",
    val telefone: String = "",
    val showSnackbar: Boolean = false,
    val snackbarMessage: String = "",
    val snackbarAction: String = "" // Usado para customizar a cor do Snackbar na UI
) {
    /** Propriedade computada para definir o texto do botão baseado no estado. */
    val textoBotao: String
        get() = if (usuarioPrincipal == null) "Criar Conta" else "Salvar Alterações"
}

// =================================================================================
// 2. VIEWMODEL: UsuarioViewModel (Lógica de Negócios)
// =================================================================================

class UsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState.asStateFlow()

    init {
        // Observa mudanças no usuário no banco de dados e atualiza o estado da UI.
        viewModelScope.launch {
            repository.buscarUsuarioUnicoFlow().collect { usuario ->
                _uiState.update { currentState ->
                    if (usuario != null) {
                        // Preenche os campos com os dados do usuário existente.
                        currentState.copy(
                            usuarioPrincipal = usuario,
                            nome = usuario.nome,
                            email = usuario.email,
                            senha = usuario.senha,
                            cpf = usuario.cpf,
                            telefone = usuario.telefone ?: "",
                            isLoading = false
                        )
                    } else {
                        // Limpa os campos se o usuário for deletado ou não existir (modo Cadastro).
                        currentState.copy(
                            usuarioPrincipal = null,
                            nome = "",
                            email = "",
                            senha = "",
                            cpf = "",
                            telefone = "",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    // --- Handlers de Mudança de Campo (Eventos) ---
    fun onNomeChange(novoNome: String) = _uiState.update { it.copy(nome = novoNome) }
    fun onEmailChange(novoEmail: String) = _uiState.update { it.copy(email = novoEmail) }
    fun onSenhaChange(novaSenha: String) = _uiState.update { it.copy(senha = novaSenha) }
    fun onCpfChange(novoCpf: String) = _uiState.update { it.copy(cpf = novoCpf) }
    fun onTelefoneChange(novoTelefone: String) = _uiState.update { it.copy(telefone = novoTelefone) }
    fun onSnackbarDismiss() = _uiState.update { it.copy(showSnackbar = false) }

    /**
     * Salva ou atualiza o usuário no banco de dados.
     */
    fun onSave() {
        val state = _uiState.value
        // Validação básica (pode ser aprimorada)
        if (state.nome.isBlank() || state.email.isBlank() || state.senha.isBlank() || state.cpf.isBlank()) return

        val telefoneFinal = state.telefone.ifBlank { null }
        val usuarioExistente = state.usuarioPrincipal

        // Determina se é uma atualização ou uma nova inserção.
        val usuarioParaSalvar = usuarioExistente?.copy(
            nome = state.nome,
            email = state.email,
            senha = state.senha,
            cpf = state.cpf,
            telefone = telefoneFinal
        ) ?: Usuario(
            nome = state.nome,
            email = state.email,
            senha = state.senha,
            cpf = state.cpf,
            telefone = telefoneFinal
        )

        viewModelScope.launch {
            val (mensagem, acao) = if (usuarioExistente == null) {
                // Inserir novo usuário
                repository.inserir(usuarioParaSalvar)
                Pair("Conta criada com sucesso!", "CREATE")
            } else {
                // Atualizar usuário existente
                repository.atualizar(usuarioParaSalvar)
                Pair("Conta editada com sucesso!", "EDIT")
            }

            // Atualiza o uiState com a nova mensagem e o usuário salvo
            _uiState.update {
                it.copy(
                    usuarioPrincipal = usuarioParaSalvar,
                    showSnackbar = true,
                    snackbarMessage = mensagem,
                    snackbarAction = acao
                )
            }
        }
    }

    /*// Atualiza o uiState com a nova mensagem e o usuário salvo
            _uiState.update {
                it.copy(
                    usuarioPrincipal = usuarioParaSalvar,
                    showSnackbar = true,
                    snackbarMessage = "",
                    snackbarAction = "CREATE"
                )
            }*/

    /**
     * Deleta o usuário do banco de dados.
     */
    fun onDelete() {
        val usuario = _uiState.value.usuarioPrincipal ?: return

        viewModelScope.launch {
            repository.deletar(usuario)

            // O flow no `init` cuidará de limpar o estado e setar `usuarioPrincipal = null`,
            // mas exibimos o Snackbar imediatamente.
            _uiState.update {
                it.copy(
                    showSnackbar = true,
                    snackbarMessage = "Conta excluída com sucesso!",
                    snackbarAction = "DELETE"
                )
            }
        }
    }
}

// =================================================================================
// 3. FACTORY: UsuarioViewModelFactory (Injeção de Dependência)
// =================================================================================

class UsuarioViewModelFactory(private val repository: UsuarioRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuarioViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}