package com.evginozan.kargotakip.domain.model

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val fullName: String,
    val phone: String,
    val token: String = ""
)