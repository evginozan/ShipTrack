package com.evginozan.kargotakip.data.remote

import com.evginozan.kargotakip.data.local.TokenManager
import com.evginozan.kargotakip.data.remote.dto.BranchDto
import com.evginozan.kargotakip.data.remote.dto.CargoResponseDto
import com.evginozan.kargotakip.data.remote.dto.DeliveryCodeResponseDto
import com.evginozan.kargotakip.data.remote.dto.DeliveryCodeVerifyRequestDto
import com.evginozan.kargotakip.data.remote.dto.LoginRequestDto
import com.evginozan.kargotakip.data.remote.dto.LoginResponseDto
import com.evginozan.kargotakip.data.remote.dto.MessageResponseDto
import com.evginozan.kargotakip.data.remote.dto.RegisterRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class KargoTakipApiService(private val client: HttpClient) {
    private val baseUrl = "http://10.0.2.2:8080"

    // Auth Service
    suspend fun login(request: LoginRequestDto): LoginResponseDto {
        return client.post("$baseUrl/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()  // body() metodu eklendi
    }

    suspend fun register(request: RegisterRequestDto): MessageResponseDto {
        return client.post("$baseUrl/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()  // body() metodu eklendi
    }

    // Cargo Service
    suspend fun getCargoByTrackingCode(trackingCode: String): CargoResponseDto {
        return client.get("$baseUrl/api/cargo/$trackingCode") {
            // Bu headers bloğunu ekleyin
            headers {
                append(HttpHeaders.Authorization, "Bearer ${TokenManager.getToken()}")
            }
        }.body()
    }

    suspend fun getCargosBySenderPhone(phone: String): List<CargoResponseDto> {
        return client.get("$baseUrl/api/cargo/sender/$phone") {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${TokenManager.getToken()}")
            }
        }.body()  // body() metodu eklendi
    }

    suspend fun getCargosByReceiverPhone(phone: String): List<CargoResponseDto> {
        return client.get("$baseUrl/api/cargo/receiver/$phone") {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${TokenManager.getToken()}")
            }
        }.body()  // body() metodu eklendi
    }

    suspend fun getDeliveryCode(trackingCode: String): DeliveryCodeResponseDto {
        return client.get("$baseUrl/api/cargo/$trackingCode/delivery-code") {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${TokenManager.getToken()}")
            }
        }.body()  // body() metodu eklendi
    }

    suspend fun verifyDeliveryCode(trackingCode: String, deliveryCode: String): CargoResponseDto {
        return client.post("$baseUrl/api/cargo/$trackingCode/verify") {
            contentType(ContentType.Application.Json)
            setBody(DeliveryCodeVerifyRequestDto(deliveryCode))
            headers {
                append(HttpHeaders.Authorization, "Bearer ${TokenManager.getToken()}")
            }
        }.body()  // body() metodu eklendi
    }

    // Branch Service
    suspend fun getAllBranches(): List<BranchDto> {
        return client.get("$baseUrl/api/branches").body()  // body() metodu eklendi
    }

    suspend fun getBranchById(id: Long): BranchDto {
        return client.get("$baseUrl/api/branches/$id").body()  // body() metodu eklendi
    }

    suspend fun getBranchesByCity(city: String): List<BranchDto> {
        return client.get("$baseUrl/api/branches/city/$city").body()  // body() metodu eklendi
    }
}