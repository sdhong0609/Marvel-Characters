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

    private val _isLoadingVisible = MutableStateFlow(false)
    val isLoadingVisible: StateFlow<Boolean> = _isLoadingVisible.asStateFlow()

    private var favoritesCount = 0
    private var limit = COUNT_PER_PAGE

    init {
        launch {
            keyword.debounce(300L).collectLatest {
                if (it.isNotBlank() && it.trim().length >= 2) {
                    limit = COUNT_PER_PAGE
                    getSearchedCharacters(keyword = it, limit = COUNT_PER_PAGE)
                }
            }
        }

        launch {
            characterRepository.getAll().collectLatest {
                favoritesCount = it.size
            }
        }
    }

    private suspend fun getSearchedCharacters(keyword: String, limit: Int) {
        _isLoadingVisible.update { true }
        if (limit == COUNT_PER_PAGE) {
            _searchedCharacters.update { emptyList() }
        }
        val ts = System.currentTimeMillis().toString()
        val response = characterRepository.getSearchedCharacters(
            ts = ts,
            apiKey = BuildConfig.API_PUBLIC_KEY,
            hash = getHash(ts),
            nameStartsWith = keyword,
            limit = limit
        )
        _searchedCharacters.update {
            response.data.results.map { it.toLocal() }
        }

        _isLoadingVisible.update { false }
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

    fun loadMoreCharacters() {
        launch {
            limit += COUNT_PER_PAGE
            getSearchedCharacters(keyword.value, limit)
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
                if (favoritesCount >= MAX_FAVORITES_COUNT) {
                    val deleteCount = favoritesCount - MAX_FAVORITES_COUNT + 1
                    characterRepository.deleteOldestItems(deleteCount)
                }
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
        private const val COUNT_PER_PAGE = 10
        private const val MAX_FAVORITES_COUNT = 5
    }
}
