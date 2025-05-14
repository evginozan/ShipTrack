package com.evginozan.kargotakip.data.repository

import com.evginozan.kargotakip.data.remote.KargoTakipApiService
import com.evginozan.kargotakip.data.remote.dto.BranchDto
import com.evginozan.kargotakip.domain.model.Branch
import com.evginozan.kargotakip.domain.repository.BranchRepository

class BranchRepositoryImpl(private val apiService: KargoTakipApiService) : BranchRepository
{

    override suspend fun getAllBranches(): Result<List<Branch>> {
        return try {
            val response = apiService.getAllBranches()
            Result.success(response.map { mapToBranch(it) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBranchById(id: Long): Result<Branch> {
        return try {
            val response = apiService.getBranchById(id)
            Result.success(mapToBranch(response))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBranchesByCity(city: String): Result<List<Branch>> {
        return try {
            val response = apiService.getBranchesByCity(city)
            Result.success(response.map { mapToBranch(it) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun mapToBranch(dto: BranchDto): Branch {
        return Branch(
            id = dto.id,
            name = dto.name,
            city = dto.city,
            district = dto.district,
            address = dto.address,
            phone = dto.phone,
            isTransferCenter = dto.isTransferCenter
        )
    }
}