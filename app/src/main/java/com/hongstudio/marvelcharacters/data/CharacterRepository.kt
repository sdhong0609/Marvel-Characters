package com.hongstudio.marvelcharacters.data

import com.hongstudio.marvelcharacters.data.source.network.SearchCharactersResponse

interface CharacterRepository {

    suspend fun getSearchedCharacters(
        ts: String,
        apiKey: String,
        hash: String
    ): SearchCharactersResponse
}
