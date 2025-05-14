package com.evginozan.kargotakip.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String,
    val type: String,
    val id: Long,
    val username: String,
    val email: String,
    val fullName: String,
    val phone: String,
    val roles: List<String>
)