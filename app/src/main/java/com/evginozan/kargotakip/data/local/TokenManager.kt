package com.evginozan.kargotakip.data.local

object TokenManager {
    private var token: String? = null

    fun saveToken(newToken: String) {
        token = newToken
    }

    fun getToken(): String? {
        return token
    }

    fun clearToken() {
        token = null
    }
}