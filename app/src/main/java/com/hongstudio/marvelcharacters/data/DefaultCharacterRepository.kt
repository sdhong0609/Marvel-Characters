package com.hongstudio.marvelcharacters.data

import com.hongstudio.marvelcharacters.data.source.local.FavoriteDao
import com.hongstudio.marvelcharacters.data.source.local.LocalCharacter
import com.hongstudio.marvelcharacters.data.source.network.SearchApi
import com.hongstudio.marvelcharacters.data.source.network.SearchCharactersResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCharacterRepository @Inject constructor(
    private val favoriteLocalDataSource: FavoriteDao,
    private val characterRemoteDataSource: SearchApi
) : CharacterRepository {

    override fun getAll(): Flow<List<LocalCharacter>> = favoriteLocalDataSource.getAll()

    override suspend fun insert(localCharacter: LocalCharacter) = favoriteLocalDataSource.insert(localCharacter)

    override suspend fun delete(localCharacter: LocalCharacter) = favoriteLocalDataSource.delete(localCharacter)

    override suspend fun deleteOldestItems() = favoriteLocalDataSource.deleteOldestItem()

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
