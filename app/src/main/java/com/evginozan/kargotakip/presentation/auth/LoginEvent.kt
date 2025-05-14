package com.evginozan.kargotakip.presentation.auth

sealed class LoginEvent {
    data class UsernameChanged(val username: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Login : LoginEvent()
    object NavigateToRegister : LoginEvent()
}