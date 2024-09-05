package com.hongstudio.marvelcharacters.data.source.network

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("v1/public/characters")
    suspend fun getSearchedCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("limit") limit: Int
    ): SearchCharactersResponse
}

