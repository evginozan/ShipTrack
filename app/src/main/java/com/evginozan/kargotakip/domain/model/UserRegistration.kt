package com.evginozan.kargotakip.domain.model

data class UserRegistration(
    val username: String,
    val password: String,
    val email: String,
    val fullName: String,
    val phone: String
)