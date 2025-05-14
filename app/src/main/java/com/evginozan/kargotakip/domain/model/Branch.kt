package com.evginozan.kargotakip.domain.model

data class Branch(
    val id: Long,
    val name: String,
    val city: String,
    val district: String,
    val address: String,
    val phone: String,
    val isTransferCenter: Boolean
)