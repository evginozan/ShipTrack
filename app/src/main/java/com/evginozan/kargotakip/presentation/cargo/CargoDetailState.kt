package com.evginozan.kargotakip.presentation.cargo

import com.evginozan.kargotakip.domain.model.Cargo

data class CargoDetailState(
    val isLoading: Boolean = false,
    val cargo: Cargo? = null,
    val errorMessage: String? = null
)