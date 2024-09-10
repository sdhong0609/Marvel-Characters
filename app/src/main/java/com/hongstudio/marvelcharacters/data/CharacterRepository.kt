package com.hongstudio.marvelcharacters.data

import com.hongstudio.marvelcharacters.data.source.local.CharacterLocal
import com.hongstudio.marvelcharacters.data.source.network.SearchCharactersResponse
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getAll(): Flow<List<CharacterLocal>>

    suspend fun insert(characterLocal: CharacterLocal)

    suspend fun delete(characterLocal: CharacterLocal)

    suspend fun deleteOldestItems(count: Int)

    suspend fun getSearchedCharacters(
        ts: String,
        apiKey: String,
        hash: String,
        nameStartsWith: String,
        limit: Int
    ): SearchCharactersResponse
}
