package com.evginozan.kargotakip.presentation.auth

data class LoginState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)