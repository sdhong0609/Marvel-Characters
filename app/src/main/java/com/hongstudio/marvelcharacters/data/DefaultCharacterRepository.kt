package com.hongstudio.marvelcharacters.data

import com.hongstudio.marvelcharacters.data.source.local.FavoriteDao
import com.hongstudio.marvelcharacters.data.source.local.CharacterLocal
import com.hongstudio.marvelcharacters.data.source.network.SearchApi
import com.hongstudio.marvelcharacters.data.source.network.SearchCharactersResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCharacterRepository @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val searchApi: SearchApi
) : CharacterRepository {

    override fun getAll(): Flow<List<CharacterLocal>> = favoriteDao.getAll()

    override suspend fun insert(characterLocal: CharacterLocal) = favoriteDao.insert(characterLocal)

    override suspend fun delete(characterLocal: CharacterLocal) = favoriteDao.delete(characterLocal)

    override suspend fun deleteOldestItems(count: Int) = favoriteDao.deleteOldestItems(count)

    override suspend fun getSearchedCharacters(
        ts: String,
        apiKey: String,
        hash: String,
        nameStartsWith: String,
        limit: Int
    ): SearchCharactersResponse {

        return searchApi.getSearchedCharacters(
            ts = ts,
            apiKey = apiKey,
            hash = hash,
            nameStartsWith = nameStartsWith,
            limit = limit
        )
    }
}
