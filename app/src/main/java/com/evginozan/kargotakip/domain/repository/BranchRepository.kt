package com.evginozan.kargotakip.domain.repository

import com.evginozan.kargotakip.domain.model.Branch

interface BranchRepository {
    suspend fun getAllBranches(): Result<List<Branch>>
    suspend fun getBranchById(id: Long): Result<Branch>
    suspend fun getBranchesByCity(city: String): Result<List<Branch>>
}