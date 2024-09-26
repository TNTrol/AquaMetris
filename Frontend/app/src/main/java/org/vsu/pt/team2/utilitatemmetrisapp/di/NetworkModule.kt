package org.vsu.pt.team2.utilitatemmetrisapp.di

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.vsu.pt.team2.utilitatemmetrisapp.BuildConfig
import org.vsu.pt.team2.utilitatemmetrisapp.api.BASE_URL
import org.vsu.pt.team2.utilitatemmetrisapp.api.interceptor.AuthInterceptor
import org.vsu.pt.team2.utilitatemmetrisapp.api.interceptor.CommonDataInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    private val TIMEOUT: Long = 30

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(CommonDataInterceptor())
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            Log.d("BuildConfig","Debug")
            Log.d("Http","Logging level : body")
            HttpLoggingInterceptor.Level.BODY
        }
        else {
            Log.d("BuildConfig","Not Debug")
            Log.d("Http","Logging level : basic")
            HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Singleton
    @Provides
    fun provideObjectMapper(): ObjectMapper = ObjectMapper()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
    }
}