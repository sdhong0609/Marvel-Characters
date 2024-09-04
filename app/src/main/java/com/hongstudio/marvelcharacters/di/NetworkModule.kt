package com.hongstudio.marvelcharacters.di

import com.hongstudio.marvelcharacters.BuildConfig
import com.hongstudio.marvelcharacters.data.source.network.SearchApi
import com.hongstudio.marvelcharacters.common.DefaultJson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val contentType = "application/json".toMediaType()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(DefaultJson.asConverterFactory(contentType))
        .build()

    @Singleton
    @Provides
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }
}
