package com.hongstudio.marvelcharacters.ui.search

import com.hongstudio.marvelcharacters.BuildConfig
import com.hongstudio.marvelcharacters.base.BaseViewModel
import com.hongstudio.marvelcharacters.data.CharacterRepository
import com.hongstudio.marvelcharacters.data.source.local.LocalCharacter
import com.hongstudio.marvelcharacters.data.toLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseViewModel() {

    private val keyword = MutableStateFlow("")

    private val _searchedCharacters = MutableStateFlow(listOf<LocalCharacter>())
    val searchedCharacters: StateFlow<List<LocalCharacter>> = _searchedCharacters.asStateFlow()

    init {
        launch {
            keyword.debounce(300L).collectLatest {
                if (it.isNotBlank() && it.trim().length >= 2) {
                    getSearchedCharacters(it)
                }
            }
        }
    }

    private suspend fun getSearchedCharacters(keyword: String) {
        val ts = System.currentTimeMillis().toString()
        val response = characterRepository.getSearchedCharacters(
            ts = ts,
            apiKey = BuildConfig.API_PUBLIC_KEY,
            hash = getHash(ts),
            nameStartsWith = keyword,
            limit = SEARCH_LIMIT
        )
        _searchedCharacters.update {
            response.data.results.map { it.toLocal() }
        }

        updateFavorites()
    }

    private suspend fun updateFavorites() {
        characterRepository.getAll().collectLatest { favorites ->
            val updatedItems = _searchedCharacters.value.map { item ->
                if (favorites.any { it.id == item.id }) {
                    item.copy(isFavorite = true)
                } else {
                    item.copy(isFavorite = false)
                }
            }
            _searchedCharacters.update { updatedItems }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getHash(ts: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest((ts + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY).toByteArray())
        return digest.toHexString()
    }

    fun onKeywordChanged(newKeyword: String) {
        keyword.value = newKeyword
    }

    fun onClickItem(item: LocalCharacter) {
        launch {
            if (item.isFavorite) {
                characterRepository.delete(item)
            } else {
                characterRepository.insert(
                    item.copy(
                        isFavorite = true,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    companion object {
        private const val SEARCH_LIMIT = 10
    }
}
