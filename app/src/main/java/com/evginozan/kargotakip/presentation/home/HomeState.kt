package com.evginozan.kargotakip.presentation.home

import com.evginozan.kargotakip.domain.model.Cargo
import com.evginozan.kargotakip.domain.model.User

data class HomeState(
    val isLoading: Boolean = false,
    val cargos: List<Cargo> = emptyList(),
    val errorMessage: String? = null,
    val trackingCode: String = "",
    val user: User? = null
)