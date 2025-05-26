// app/src/main/java/com/example/mcnews/data/repo/AuthRepositoryImpl.kt
package com.example.mcnews.data.repo

import android.content.Context
import android.util.Log
import com.example.mcnews.domain.repo.AuthRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject
import androidx.core.content.edit

class AuthRepositoryImpl @Inject constructor(
    private val context: Context,
    private val client: OkHttpClient
) : AuthRepository {

    override suspend fun login(login: String, password: String): Boolean {
        val requestBody = Gson().toJson(mapOf("Login" to login, "PasswordHash" to password))
        Log.d("AuthRepository", "Login request body: $requestBody")
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/auth/login")
            .post(requestBody.toRequestBody("application/json".toMediaType()))
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                Log.d("AuthRepository", "Login response: ${response.code} ${response.message}")
                if (response.isSuccessful) {
                    val responseBody = response.body?.string() ?: return false
                    Log.d("AuthRepository", "Login response body: $responseBody")
                    val json = JSONObject(responseBody)
                    val token = json.getString("access_token")
                    Log.d("AuthRepository", "Saving token: $token")
                    context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                        .edit() {
                            putString("jwt_token", token)
                        }
                    true
                } else {
                    Log.e("AuthRepository", "Login failed: ${response.body?.string()}")
                    false
                }
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login error", e)
            false
        }
    }

    override suspend fun register(login: String, password: String, firstName: String?, lastName: String?): Boolean {
        val requestBody = Gson().toJson(
            mapOf(
                "Login" to login,
                "PasswordHash" to password,
                "FirstName" to (firstName ?: ""),
                "LastName" to (lastName ?: ""),
                "MiddleName" to null,
                "BirthDate" to "1990-01-01",
                "GenderId" to 1,
                "Email" to "$login@example.com",
                "Photo" to null
            )
        )
        Log.d("AuthRepository", "Register request body: $requestBody")
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/auth/register")
            .post(requestBody.toRequestBody("application/json".toMediaType()))
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                Log.d("AuthRepository", "Register response: ${response.code} ${response.message}")
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d("AuthRepository", "Register response body: $responseBody")
                    true
                } else {
                    val errorBody = response.body?.string()
                    Log.e("AuthRepository", "Register failed: $errorBody")
                    false
                }
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Register error", e)
            false
        }
    }
}