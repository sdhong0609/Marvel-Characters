package com.hongstudio.marvelcharacters.ui.favorite

import com.hongstudio.marvelcharacters.base.BaseViewModel
import com.hongstudio.marvelcharacters.data.CharacterRepository
import com.hongstudio.marvelcharacters.data.source.local.LocalCharacter
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

    private val _favoriteCharacters = MutableStateFlow(listOf<LocalCharacter>())
    val favoriteCharacters: StateFlow<List<LocalCharacter>> = _favoriteCharacters.asStateFlow()

    init {
        launch {
            characterRepository.getAll().collectLatest { favorites ->
                _favoriteCharacters.update { favorites }
            }
        }
    }

    fun deleteFavorite(item: LocalCharacter) {
        launch {
            characterRepository.delete(item)
        }
    }
}
