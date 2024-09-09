package com.hongstudio.marvelcharacters.ui.favorite

import com.hongstudio.marvelcharacters.base.BaseViewModel
import com.hongstudio.marvelcharacters.data.CharacterRepository
import com.hongstudio.marvelcharacters.data.source.local.CharacterLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseViewModel() {

    private val _favoriteCharacters = MutableStateFlow(listOf<CharacterLocal>())
    val favoriteCharacters: StateFlow<List<CharacterLocal>> = _favoriteCharacters.asStateFlow()

    private val _isLoadingVisible = MutableStateFlow(false)
    val isLoadingVisible: StateFlow<Boolean> = _isLoadingVisible.asStateFlow()

    init {
        launch {
            characterRepository.getAll().collectLatest { favorites ->
                _isLoadingVisible.update { true }
                _favoriteCharacters.update { favorites }
                _isLoadingVisible.update { false }
            }
        }
    }

    fun deleteFavorite(item: CharacterLocal) {
        launch {
            _isLoadingVisible.update { true }
            characterRepository.delete(item)
            _isLoadingVisible.update { false }
        }
    }
}
