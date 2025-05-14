package com.evginozan.kargotakip.presentation.profile

import com.evginozan.kargotakip.domain.model.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null,
    val isLoggedOut: Boolean = false
)