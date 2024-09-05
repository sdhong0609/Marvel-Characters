package com.hongstudio.marvelcharacters.data

import com.hongstudio.marvelcharacters.data.source.local.LocalCharacter
import com.hongstudio.marvelcharacters.data.source.network.SearchCharactersResponse
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getAll(): Flow<List<LocalCharacter>>

    suspend fun insert(localCharacter: LocalCharacter)

    suspend fun delete(localCharacter: LocalCharacter)

    suspend fun deleteOldestItems()

    suspend fun getSearchedCharacters(
        ts: String,
        apiKey: String,
        hash: String,
        nameStartsWith: String,
        limit: Int
    ): SearchCharactersResponse
}
