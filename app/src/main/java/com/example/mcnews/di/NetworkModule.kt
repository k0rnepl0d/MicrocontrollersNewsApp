package com.example.mcnews.di

import android.content.Context
import android.util.Log
import com.example.mcnews.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Модуль Dagger Hilt для предоставления зависимостей, связанных с сетевыми запросами.
 * Настраивает OkHttpClient с интерцептором для добавления токена авторизации и Retrofit для API.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Предоставляет OkHttpClient с интерцептором, добавляющим заголовок Authorization.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                    .getString("jwt_token", null)
                val request = chain.request().newBuilder()
                    .apply {
                        if (token != null) {
                            addHeader("Authorization", "Bearer $token")
                            Log.d("NetworkModule", "Added Authorization header: Bearer $token")
                        } else {
                            Log.w("NetworkModule", "No token found")
                        }
                    }
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    /**
     * Предоставляет экземпляр Retrofit для выполнения API-запросов.
     */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    /**
     * Предоставляет сервис API на основе Retrofit.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}