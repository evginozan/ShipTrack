package com.evginozan.kargotakip.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val username: String,
    val password: String,
    val email: String,
    val fullName: String,
    val phone: String,
    val roles: Set<String> = setOf("customer")
)