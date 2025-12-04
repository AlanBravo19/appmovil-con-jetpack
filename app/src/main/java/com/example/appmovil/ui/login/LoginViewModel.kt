package com.example.appmovil.ui.login

import androidx.lifecycle.ViewModel
import com.example.appmovil.data.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String? = null
)

class LoginViewModel(private val session: SessionManager) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChanged(value: String) {
        _uiState.value = _uiState.value.copy(email = value, errorMessage = null)
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(password = value, errorMessage = null)
    }

    fun login(): Boolean {
        val state = _uiState.value

        val savedEmail: String? = session.getEmail()
        val savedPassword: String? = session.getPassword()

        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Todos los campos son obligatorios")
            return false
        }

        return if (state.email == savedEmail && state.password == savedPassword) {
            true
        } else {
            _uiState.value = state.copy(errorMessage = "Correo o contraseña incorrectos")
            false
        }
    }
}
