package com.example.mcnews.domain.repo

interface AuthRepository {
    suspend fun login(login: String, password: String): Boolean
    suspend fun register(login: String, password: String, firstName: String?, lastName: String?): Boolean
}