package com.hongstudio.marvelcharacters.data

import com.hongstudio.marvelcharacters.data.source.network.SearchApi
import com.hongstudio.marvelcharacters.data.source.network.SearchCharactersResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCharacterRepository @Inject constructor(
    private val characterRemoteDataSource: SearchApi
) : CharacterRepository {

    override suspend fun getSearchedCharacters(
        ts: String,
        apiKey: String,
        hash: String,
        nameStartsWith: String,
        limit: Int
    ): SearchCharactersResponse {

        return characterRemoteDataSource.getSearchedCharacters(
            ts = ts,
            apiKey = apiKey,
            hash = hash,
            nameStartsWith = nameStartsWith,
            limit = limit
        )
    }
}
