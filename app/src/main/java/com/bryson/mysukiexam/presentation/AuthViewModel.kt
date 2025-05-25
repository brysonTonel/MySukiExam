package com.bryson.mysukiexam.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryson.mysukiexam.domain.model.LoginState
import com.bryson.mysukiexam.domain.model.RegisterState
import com.bryson.mysukiexam.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState?>(LoginState.Idle)
    val loginState: StateFlow<LoginState?> = _loginState

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = repository.login(username, password)
            _loginState.value = result
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            val result = repository.register(username, password)
            _registerState.value = result
        }
    }
    fun logout() {
        _loginState.value = null
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }
}