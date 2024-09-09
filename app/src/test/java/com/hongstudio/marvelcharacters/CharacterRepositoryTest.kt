package com.hongstudio.marvelcharacters

import com.hongstudio.marvelcharacters.data.CharacterRepository
import com.hongstudio.marvelcharacters.data.DefaultCharacterRepository
import com.hongstudio.marvelcharacters.data.source.local.FavoriteDao
import com.hongstudio.marvelcharacters.data.source.local.CharacterLocal
import com.hongstudio.marvelcharacters.data.source.network.Character
import com.hongstudio.marvelcharacters.data.source.network.Data
import com.hongstudio.marvelcharacters.data.source.network.SearchApi
import com.hongstudio.marvelcharacters.data.source.network.SearchCharactersResponse
import com.hongstudio.marvelcharacters.utils.getHash
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CharacterRepositoryTest {

    private val favoriteDao: FavoriteDao = object : FavoriteDao {
        override fun getAll(): Flow<List<CharacterLocal>> {
            TODO("Not yet implemented")
        }

        override suspend fun insert(characterLocal: CharacterLocal) {
            TODO("Not yet implemented")
        }

        override suspend fun delete(characterLocal: CharacterLocal) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteOldestItems(count: Int) {
            TODO("Not yet implemented")
        }

    }

    private val searchApi: SearchApi = object : SearchApi {
        override suspend fun getSearchedCharacters(
            ts: String,
            apiKey: String,
            hash: String,
            nameStartsWith: String,
            limit: Int
        ): SearchCharactersResponse {
            return SearchCharactersResponse(
                Data(
                    listOf(
                        Character(
                            id = 1011379,
                            name = "Iron Cross Army",
                            description = "",
                            thumbnail = Character.Thumbnail(
                                path = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available",
                                extension = "jpg"
                            )
                        ),
                        Character(
                            id = 1011318,
                            name = "Iron Fist (Bei Bang-Wen)",
                            description = "",
                            thumbnail = Character.Thumbnail(
                                path = "http://i.annihil.us/u/prod/marvel/i/mg/9/20/53176ebd40ad7",
                                extension = "jpg"
                            )
                        )
                    )
                )
            )
        }
    }

    private val characterRepository: CharacterRepository = DefaultCharacterRepository(
        favoriteDao = favoriteDao,
        searchApi = searchApi
    )


    @Test
    fun `api응답을 그대로 잘 전달`() = runTest {
        val ts = System.currentTimeMillis().toString()
        val response = characterRepository.getSearchedCharacters(
            ts = ts,
            apiKey = BuildConfig.API_PUBLIC_KEY,
            hash = getHash(ts),
            nameStartsWith = "ir",
            limit = 2
        )
        val results = response.data.results
        assert(results.size == 2)

        assert(results[0].id == 1011379)
        assert(results[0].name == "Iron Cross Army")
        assert(results[0].description == "")
        assert(results[0].thumbnail.path == "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available")
        assert(results[0].thumbnail.extension == "jpg")

        assert(results[1].id == 1011318)
        assert(results[1].name == "Iron Fist (Bei Bang-Wen)")
        assert(results[1].description == "")
        assert(results[1].thumbnail.path == "http://i.annihil.us/u/prod/marvel/i/mg/9/20/53176ebd40ad7")
        assert(results[1].thumbnail.extension == "jpg")
    }
}
